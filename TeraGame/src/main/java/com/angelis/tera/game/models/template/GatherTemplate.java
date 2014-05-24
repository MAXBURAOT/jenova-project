package com.angelis.tera.game.models.template;

import com.angelis.tera.game.models.gather.enums.GatherTypeNameEnum;

public class GatherTemplate extends Template {

    private int grade;
    private GatherTypeNameEnum gatherTypeName;

    public int getGrade() {
        return grade;
    }

    public void setGrade(final int grade) {
        this.grade = grade;
    }

    public GatherTypeNameEnum getGatherTypeName() {
        return gatherTypeName;
    }

    public void setGatherTypeName(final GatherTypeNameEnum gatherTypeName) {
        this.gatherTypeName = gatherTypeName;
    }
}
