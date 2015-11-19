package com.iidooo.weixin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.iidooo.weixin.constant.WeixinConstant;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

public class CallbackServlet extends HttpServlet{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static Logger logger = Logger.getLogger(CallbackServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String sVerifyMsgSig = req.getParameter(WeixinConstant.KEY_MSG_SIGNATURE);
            String sVerifyTimeStamp = req.getParameter(WeixinConstant.KEY_TIMESTAMP);
            String sVerifyNonce = req.getParameter(WeixinConstant.KEY_NONCE);
            String sVerifyEchoStr = req.getParameter(WeixinConstant.KEY_ECHOSTR);

            String token = req.getParameter(WeixinConstant.KEY_TOKEN);
            String aesKey = req.getParameter(WeixinConstant.KEY_AES_KEY);
            String coprID = req.getParameter(WeixinConstant.KEY_CORPID);
            
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, aesKey, coprID);
            String sEchoStr = wxcpt.VerifyURL(sVerifyMsgSig, sVerifyTimeStamp, sVerifyNonce, sVerifyEchoStr);
            System.out.println("verifyurl echostr: " + sEchoStr);

            resp.getWriter().print(sEchoStr);
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
