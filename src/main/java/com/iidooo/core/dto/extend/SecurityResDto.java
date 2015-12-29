package com.iidooo.core.dto.extend;

import java.util.ArrayList;
import java.util.List;

import com.iidooo.core.dto.generate.SecurityRes;

/**
 * @author Ethan
 * 
 */
public class SecurityResDto extends SecurityRes {

    // The children resource of this SecurityResourceDto
    private List<SecurityResDto> children;

    private List<SecurityResDto> offspring;
    
    private boolean isSelected;

    public List<SecurityResDto> getChildren() {
        if (children == null) {
            children = new ArrayList<SecurityResDto>();
        }
        return children;
    }

    public void setChildren(List<SecurityResDto> children) {
        this.children = children;
    }

    public List<SecurityResDto> getOffspring() {
        if (offspring == null) {
            offspring = new ArrayList<SecurityResDto>();
        }
        return offspring;
    }

    public void setOffspring(List<SecurityResDto> offspring) {
        this.offspring = offspring;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

}
