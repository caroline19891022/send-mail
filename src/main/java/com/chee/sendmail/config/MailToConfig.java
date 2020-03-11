package com.chee.sendmail.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties("mailto")
public class MailToConfig {
    private List<Client> client;
    private List<Manager> manager;

    public List<Client> getClient() {
        return client;
    }

    public void setClient(List<Client> client) {
        this.client = client;
    }

    public List<Manager> getManager() {
        return manager;
    }

    public void setManager(List<Manager> manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "MailToConfig{" +
                "client=" + client +
                ", manager=" + manager +
                '}';
    }

    public static class Client {
        private String nickname;
        private String mailAddress;
        private String group;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMailAddress() {
            return mailAddress;
        }

        public void setMailAddress(String mailAddress) {
            this.mailAddress = mailAddress;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        @Override
        public String toString() {
            return "Client{" +
                    "nickname='" + nickname + '\'' +
                    ", mailAddress='" + mailAddress + '\'' +
                    ", group='" + group + '\'' +
                    '}';
        }
    }
    public static class Manager {
        private String nickname;
        private String mailAddress;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMailAddress() {
            return mailAddress;
        }

        public void setMailAddress(String mailAddress) {
            this.mailAddress = mailAddress;
        }

        @Override
        public String toString() {
            return "Manager{" +
                    "nickname='" + nickname + '\'' +
                    ", mailAddress='" + mailAddress + '\'' +
                    '}';
        }
    }
}
