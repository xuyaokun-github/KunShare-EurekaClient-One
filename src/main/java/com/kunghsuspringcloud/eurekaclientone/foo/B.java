package com.kunghsuspringcloud.eurekaclientone.foo;

import javax.management.MBeanRegistration;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;

public class B extends C implements MBeanRegistration, Referenceable {

    private String name = "xxxx";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ObjectName preRegister(MBeanServer server, ObjectName name) throws Exception {
        return null;
    }

    @Override
    public void postRegister(Boolean registrationDone) {

    }

    @Override
    public void preDeregister() throws Exception {

    }

    @Override
    public void postDeregister() {

    }

    public Reference getReference() throws NamingException {
        final String className = getClass().getName();
        final String factoryName = className + "Factory"; // XXX: not robust
        Reference ref = new Reference(className, factoryName, null);
        ref.add(new StringRefAddr("instanceKey", ""));
        ref.add(new StringRefAddr("url", ""));
        ref.add(new StringRefAddr("username", ""));
        ref.add(new StringRefAddr("password", ""));
        // TODO ADD OTHER PROPERTIES
        return ref;
    }
}
