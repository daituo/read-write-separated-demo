package com.read.write.separated.demo.readwriteseparated.entity;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* 请添加描述
* @author FGVTH
* @time 2019-03-21
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resource implements Serializable {
    /**
    * 
    */
    @ApiModelProperty(notes = "")
    private Long id;

    /**
    * 资源名称
    */
    @ApiModelProperty(notes = "资源名称")
    private String name;

    /**
    * 资源类型  menu-菜单    button-按钮
    */
    @ApiModelProperty(notes = "资源类型  menu-菜单    button-按钮")
    private String type;

    /**
    * 资源url
    */
    @ApiModelProperty(notes = "资源url")
    private String url;

    /**
    * 父资源id
    */
    @ApiModelProperty(notes = "父资源id")
    private Long parentId;

    /**
    * 
    */
    @ApiModelProperty(notes = "")
    private String parentIds;

    /**
    * 资源所需权限
    */
    @ApiModelProperty(notes = "资源所需权限")
    private String permission;

    /**
    * 是否可用   Y-可用   N-不可用
    */
    @ApiModelProperty(notes = "是否可用   Y-可用   N-不可用")
    private String available;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", url=").append(url);
        sb.append(", parentId=").append(parentId);
        sb.append(", parentIds=").append(parentIds);
        sb.append(", permission=").append(permission);
        sb.append(", available=").append(available);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}