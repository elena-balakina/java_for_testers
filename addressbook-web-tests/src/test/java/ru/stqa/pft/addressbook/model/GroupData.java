package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@XStreamAlias("group")
@Entity
@Table(name = "group_list")
public class GroupData {
    @XStreamOmitField //не хотим, чтобы id сохранялся в XML-файл
    @Id                         // привязка к БД
    @Column(name = "group_id") // привязка к БД
    private int groupId = Integer.MAX_VALUE; // id группы будет больше всех и она окажется самой последней при сортировке;

    @Expose // поле будет включено в файл Json
    @Column(name = "group_name")
    private String groupName;

    @Expose // поле будет включено в файл Json
    @Column(name = "group_header")
    @Type(type = "text") //многострочное текстовое поле в БД
    private String groupHeader;

    @Expose // поле будет включено в файл Json
    @Column(name = "group_footer")
    @Type(type = "text") //многострочное текстовое поле в БД
    private String groupFooter;

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

    public GroupData withId(int groupId) {
        this.groupId = groupId;
        return this;
    }

    public GroupData withName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public GroupData withHeader(String groupHeader) {
        this.groupHeader = groupHeader;
        return this;
    }

    public GroupData withFooter(String groupFooter) {
        this.groupFooter = groupFooter;
        return this;
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
                Objects.equals(groupName, groupData.groupName) &&
                Objects.equals(groupHeader, groupData.groupHeader) &&
                Objects.equals(groupFooter, groupData.groupFooter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, groupName, groupHeader, groupFooter);
    }
}
