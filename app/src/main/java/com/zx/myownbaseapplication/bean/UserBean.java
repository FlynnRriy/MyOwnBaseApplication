package com.zx.myownbaseapplication.bean;

import java.util.List;

/**
 * 用户
 *{
 *   "ticketInfo": {
 *     "ticket": "string",
 *     "userInfo": {
 *       "id": "string",
 *       "groups": [
 *         {
 *           "id": 11,
 *           "name": "文稿审核组"
 *         }
 *       ],
 *       "roles": [
 *         {
 *           "id": 11,
 *           "name": "文稿审核角色"
 *         }
 *       ],
 *       "ldapUser": {
 *         "username": "admin",
 *         "nickname": "管理员",
 *         "organization": "网博视界-产品中心"
 *       }
 *     }
 *   }
 * }
 * */
public class UserBean extends BaseBean{

    TicketInfo ticketInfo;

    public TicketInfo getTicketInfo() {
        return ticketInfo;
    }

    public void setTicketInfo(TicketInfo ticketInfo) {
        this.ticketInfo = ticketInfo;
    }

    public static class TicketInfo{
        String ticket;
        UserInfo userInfo;

        public String getTicket() {
            return ticket;
        }

        public void setTicket(String ticket) {
            this.ticket = ticket;
        }

        public UserInfo getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
        }
    }

    public static class UserInfo{
        String id;
        List<Groups> groups;
        List<Roles> roles;
        LdapUser ldapUser;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<Groups> getGroups() {
            return groups;
        }

        public void setGroups(List<Groups> groups) {
            this.groups = groups;
        }

        public List<Roles> getRoles() {
            return roles;
        }

        public void setRoles(List<Roles> roles) {
            this.roles = roles;
        }

        public LdapUser getLdapUser() {
            return ldapUser;
        }

        public void setLdapUser(LdapUser ldapUser) {
            this.ldapUser = ldapUser;
        }
    }
    public static class Groups{
        int id;
        String name;
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public static class Roles{
        int id;
        String name;
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public static class LdapUser{
        String username;
        String nickname;
        String organization;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getOrganization() {
            return organization;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }
    }

}
