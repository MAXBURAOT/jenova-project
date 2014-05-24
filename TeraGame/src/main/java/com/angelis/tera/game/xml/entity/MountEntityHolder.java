package com.angelis.tera.game.xml.entity;

import java.util.Set;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javolution.util.FastSet;

import com.angelis.tera.common.xml.entity.AbstractXMLEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mounts", namespace = "http://angelis.com/mounts")
public class MountEntityHolder extends AbstractXMLEntity {

    private static final long serialVersionUID = 4630059910453310920L;

    @XmlElement(name = "mount", namespace = "http://angelis.com/mounts")
    private Set<MountEntity> mounts;

    public Set<MountEntity> getMounts() {
        if (mounts == null) {
            mounts = new FastSet<>(0);
        }
        return mounts;
    }

    @Override
    public void afterUnmarshalling(final Unmarshaller unmarshaller) {
    }

    @Override
    public void onLoad() {
    }

}
