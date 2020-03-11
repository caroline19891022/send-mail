package com.chee.sendmail.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties("groupset")
public class GroupSetConfig {
    List<Group> group;

    public List<Group> getGroup() {
        return group;
    }

    public void setGroup(List<Group> group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "GroupSetConfig{" +
                "group=" + group +
                '}';
    }

    public static class Group {
        private String name;
        private List<String> member;

        @Override
        public String toString() {
            return "Group{" +
                    "name='" + name + '\'' +
                    ", member=" + member +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getMember() {
            return member;
        }

        public void setMember(List<String> member) {
            this.member = member;
        }
    }
}
