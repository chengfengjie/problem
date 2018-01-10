package problem.no.enums.user;

import problem.no.enums.BaseEnum;

public enum UserRole implements BaseEnum<UserRole, Integer> {
    SUPER_ADMIN(1, "超级管理员"),
    NORMAL_USER(2, "普通用户");

    private Integer code;
    private String displayName;

    UserRole(Integer code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }
}
