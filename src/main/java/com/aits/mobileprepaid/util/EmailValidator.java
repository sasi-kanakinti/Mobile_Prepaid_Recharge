package com.aits.mobileprepaid.util;

import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

public class EmailValidator {

    /**
     * Check if an email domain has valid MX records (real mail server).
     * Returns false if the domain is invalid or doesn't exist.
     */
    public static boolean hasValidMXRecord(String email) {
        try {
            if (email == null || !email.contains("@")) return false;
            String domain = email.substring(email.indexOf("@") + 1);

            Hashtable<String, String> env = new Hashtable<>();
            env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            DirContext dirContext = new InitialDirContext(env);

            var attrs = dirContext.getAttributes(domain, new String[]{"MX"});
            return attrs.get("MX") != null;
        } catch (Exception e) {
            return false;
        }
    }
}
