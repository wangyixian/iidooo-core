package com.iidooo.core.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iidooo.core.constant.DictConstant;
import com.iidooo.core.mapper.DictItemMapper;
import com.iidooo.core.mapper.HisUserExpMapper;
import com.iidooo.core.mapper.SecurityUserMapper;
import com.iidooo.core.model.po.DictItem;
import com.iidooo.core.model.po.HisUserExp;
import com.iidooo.core.model.po.SecurityUser;
import com.iidooo.core.service.SecurityUserService;
import com.iidooo.core.util.DateUtil;
import com.iidooo.core.util.SecurityUtil;
import com.iidooo.core.util.StringUtil;

@Service
public class SecurityUserServiceImpl implements SecurityUserService {

    private static final Logger logger = Logger.getLogger(SecurityUserServiceImpl.class);

    @Autowired
    private DictItemMapper dictItemMapper;

    @Autowired
    private SecurityUserMapper securityUserMapper;

    @Autowired
    private HisUserExpMapper hisUserExpMapper;

    @Override
    public SecurityUser getSecurityUserByID(Integer userID) {
        try {
            SecurityUser result = securityUserMapper.selectByUserID(userID);
            return result;
        } catch (Exception e) {
            logger.fatal(e);
            throw e;
        }
    }

    @Override
    public SecurityUser getSecurityUserByEmail(String email) {
        try {
            SecurityUser result = securityUserMapper.selectByEmail(email);
            return result;
        } catch (Exception e) {
            logger.fatal(e);
            throw e;
        }
    }

    @Override
    public SecurityUser createDefaultUser(String photoURL, String email) {
        try {
            SecurityUser user = new SecurityUser();
            user.setLoginID(StringUtil.getRandomStr(6));
            user.setPassword(SecurityUtil.getMd5("123456"));
            user.setUserName(user.getLoginID());
            user.setBirthday(new Date());
            user.setEmail(email);
            user.setIsDisable(0);
            user.setIsSilent(0);
            user.setLevel(1);
            user.setMobile("");
            user.setPhotoURL(photoURL);
            user.setPoints(0);
            user.setExperience(0);
            user.setSex("2");
            user.setWeixinID("");
            user.setUserType("2");
            user.setRemarks("");
            user.setCreateTime(new Date());
            user.setCreateUserID(1);
            user.setUpdateTime(new Date());
            user.setUpdateUserID(1);
            user.setIsDelete(0);
            user.setVersion(1);
            
            // 创建用户失败
            if (securityUserMapper.insertSelective(user) <= 0) {
                return null;
            }

            // 为了用户唯一性，用UserID再更新一遍用户名
            user.setUserName(user.getUserName() + user.getUserID().toString());
            user.setLoginID(user.getUserName());
            if (securityUserMapper.updateByUserID(user) <= 0) {
                return null;
            }

            return user;
        } catch (Exception e) {
            logger.fatal(e);
            throw e;
        }
    }

    @Override
    public SecurityUser updateUserInfo(SecurityUser securityUser) {
        try {
            securityUser.setUpdateTime(new Date());
            securityUser.setUpdateUserID(securityUser.getUserID());
            // 更新失败
            if (securityUserMapper.updateByUserID(securityUser) <= 0) {
                return null;
            }

            securityUser = securityUserMapper.selectByUserID(securityUser.getUserID());

            return securityUser;

        } catch (Exception e) {
            logger.fatal(e);
            throw e;
        }
    }

    @Override
    @Transactional
    public SecurityUser updateUserExp(Integer userID, Integer expUp, Integer isLimited) throws Exception {
        try {
            // 得到字典表里设定的经验值上限和经验值积分转换比率
            List<DictItem> dictItems = dictItemMapper.selectByClassCode(DictConstant.DICT_CLASS_EXPERIENCE);
            int expLimitedValue = 0;
            int expPointsRate = 0;
            for (DictItem item : dictItems) {
                switch (item.getDictItemCode()) {
                case DictConstant.DICT_ITEM_EXP_LIMITED_VALUE:
                    expLimitedValue = Integer.parseInt(item.getDictItemValue());
                    break;
                case DictConstant.DICT_ITEM_EXP_POINTS_RATE:
                    expPointsRate = Integer.parseInt(item.getDictItemValue());
                    break;
                }
            }

            // 如果是有经验值当日上限，那么得到当日的经验值累计
            int expHisSum = 0;
            if (isLimited != 0) {
                String today = DateUtil.getNow(DateUtil.DATE_HYPHEN);
                String startTime = today + " 00:00:00";
                String endTime = today + " 23:59:59";
                List<HisUserExp> hisUserExps = hisUserExpMapper.selectByUserID(userID, startTime, endTime);
                for (HisUserExp item : hisUserExps) {
                    if (item.getExperience() > 0) {
                        expHisSum = expHisSum + item.getExperience();
                    }
                }

                // 经验值超过了上限
                if (expHisSum >= expLimitedValue) {
                    return null;
                }
            }

            HisUserExp hisUserExp = new HisUserExp();
            hisUserExp.setUserID(userID);
            hisUserExp.setRemarks("");
            hisUserExp.setCreateUserID(userID);
            hisUserExp.setCreateTime(new Date());
            hisUserExp.setUpdateUserID(userID);
            hisUserExp.setUpdateTime(new Date());

            SecurityUser securityUser = securityUserMapper.selectByUserID(userID);
            // 如果经验值累加后超过当日上限，则设定为今日上限值
            if ((isLimited != 0) && (expHisSum + expUp > expLimitedValue)) {
                hisUserExp.setExperience(expLimitedValue - securityUser.getExperience());
                hisUserExp.setPoints(hisUserExp.getExperience() * expPointsRate);
                securityUser.setExperience(securityUser.getExperience() + hisUserExp.getExperience());
                securityUser.setPoints(securityUser.getPoints() + hisUserExp.getExperience() * expPointsRate);
            } else {
                hisUserExp.setExperience(expUp);
                hisUserExp.setPoints(expUp * expPointsRate);
                securityUser.setExperience(securityUser.getExperience() + expUp);
                securityUser.setPoints(securityUser.getPoints() + expUp * expPointsRate);
            }

            // 创建用户积分变更记录
            if (hisUserExpMapper.insert(hisUserExp) <= 0) {
                throw new Exception();
            }

            // 计算等级
            // 每升一级所需的经验值（T代表经验值，N代表当前等级）：
            // T=2×N+5 就是当前是5级的情况下，升级到6级需要2*5+5=15分
            int level = securityUser.getLevel();
            int nextLevelExp = 2 * level + 5;
            if (nextLevelExp <= securityUser.getExperience()) {
                securityUser.setLevel(level + 1);
                securityUser.setExperience(securityUser.getExperience() - nextLevelExp);

                // 消耗了经验，需要增加变更记录
                HisUserExp hisUserExpDown = new HisUserExp();
                hisUserExpDown.setUserID(userID);
                hisUserExpDown.setRemarks("");
                hisUserExpDown.setCreateUserID(userID);
                hisUserExpDown.setCreateTime(new Date());
                hisUserExpDown.setUpdateUserID(userID);
                hisUserExpDown.setUpdateTime(new Date());
                hisUserExpDown.setExperience(-nextLevelExp);
                // 创建用户积分变更记录
                if (hisUserExpMapper.insert(hisUserExpDown) <= 0) {
                    throw new Exception();
                }
            }

            if (securityUserMapper.updateByUserID(securityUser) <= 0) {
                throw new Exception();
            }
            return securityUser;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            throw e;
        }
    }

}
