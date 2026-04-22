package com.neusoft.yth.nepBackend.grid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 区域网格信息
 * </p>
 *
 * @author YTH
 * @since 2024-05-29
 */
@Getter
@Setter
@TableName("grid")
@ApiModel(value = "Grid对象", description = "区域网格信息")
public class Grid implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("网格编号")
    @TableId(value = "grid_id", type = IdType.AUTO)
    private Integer gridId;

    @ApiModelProperty("网格名称")
    @TableField("grid_name")
    private String gridName;

    @ApiModelProperty("所属父级网格ID")
    @TableField("grid_pid")
    private Integer gridPid;

    @ApiModelProperty("区域的状态")
    @TableField("status")
    private Integer status;
}
