package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class GroupData {
    private int groupId;
    private final String groupName;
    private final String groupHeader;
    private final String groupFooter;

    public GroupData(int groupId, String groupName, String groupHeader, String groupFooter) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupHeader = groupHeader;
        this.groupFooter = groupFooter;
    }

    public GroupData(String groupName, String groupHeader, String groupFooter) {
        this.groupId = 0;
        this.groupName = groupName;
        this.groupHeader = groupHeader;
        this.groupFooter = groupFooter;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupHeader() {
        return groupHeader;
    }

    public String getGroupFooter() {
        return groupFooter;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "GroupData{" +
                "groupId='" + groupId + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupData groupData = (GroupData) o;
        return groupId == groupData.groupId &&
                Objects.equals(groupName, groupData.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, groupName);
    }
}
