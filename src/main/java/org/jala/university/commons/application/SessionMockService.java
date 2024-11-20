package org.jala.university.commons.application;

import org.jala.university.commons.application.service.SessionService;

public class SessionMockService implements SessionService {

    private static final String STR_DEFAULT_ACCOUNT_NUMBER = "123456";
    private static SessionService instance;

    public static class InstanceHolder {
        public static SessionService instance = new SessionMockService();
    }

    public static SessionService getInstance() {
        return InstanceHolder.instance;
    }

    private final String accountId;

    private SessionMockService() {
        this.accountId = STR_DEFAULT_ACCOUNT_NUMBER;
    }

    @Override
    public String getAccountNumber() {
        return this.accountId;
    }

}
