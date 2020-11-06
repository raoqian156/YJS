package com.yskrq.yjs.bean;

import com.yskrq.net_library.BaseBean;

import java.util.List;

public class DianTypeBean extends BaseBean {
  private List<TypeValueBean> Type_Value;
  private List<?> CdTech_Value;
  private List<?> TypeCdTech_Value;
  private List<?> userid_Value;

  public List<TypeValueBean> getType_Value() {
    return Type_Value;
  }

  public void setType_Value(List<TypeValueBean> Type_Value) {
    this.Type_Value = Type_Value;
  }

  public List<?> getCdTech_Value() {
    return CdTech_Value;
  }

  public void setCdTech_Value(List<?> CdTech_Value) {
    this.CdTech_Value = CdTech_Value;
  }

  public List<?> getTypeCdTech_Value() {
    return TypeCdTech_Value;
  }

  public void setTypeCdTech_Value(List<?> TypeCdTech_Value) {
    this.TypeCdTech_Value = TypeCdTech_Value;
  }

  public List<?> getUserid_Value() {
    return userid_Value;
  }

  public void setUserid_Value(List<?> userid_Value) {
    this.userid_Value = userid_Value;
  }

  public static class TypeValueBean {
    /**
     * GroupId : null
     * ShopsId : 226691
     * Code : 0000
     * Description : 足浴技师
     * IsDefault : True
     * IsUse : True
     * BrandSoundListRow : 2
     * RoomCode : 0000
     * IsDownLoad : null
     */

    private Object GroupId;
    private String ShopsId;
    private String Code;
    private String Description;
    private String IsDefault;
    private String IsUse;
    private String BrandSoundListRow;
    private String RoomCode;
    private Object IsDownLoad;

    public Object getGroupId() {
      return GroupId;
    }

    public void setGroupId(Object GroupId) {
      this.GroupId = GroupId;
    }

    public String getShopsId() {
      return ShopsId;
    }

    public void setShopsId(String ShopsId) {
      this.ShopsId = ShopsId;
    }

    public String getCode() {
      return Code;
    }

    public void setCode(String Code) {
      this.Code = Code;
    }

    public String getDescription() {
      return Description;
    }

    public void setDescription(String Description) {
      this.Description = Description;
    }

    public String getIsDefault() {
      return IsDefault;
    }

    public void setIsDefault(String IsDefault) {
      this.IsDefault = IsDefault;
    }

    public String getIsUse() {
      return IsUse;
    }

    public void setIsUse(String IsUse) {
      this.IsUse = IsUse;
    }

    public String getBrandSoundListRow() {
      return BrandSoundListRow;
    }

    public void setBrandSoundListRow(String BrandSoundListRow) {
      this.BrandSoundListRow = BrandSoundListRow;
    }

    public String getRoomCode() {
      return RoomCode;
    }

    public void setRoomCode(String RoomCode) {
      this.RoomCode = RoomCode;
    }

    public Object getIsDownLoad() {
      return IsDownLoad;
    }

    public void setIsDownLoad(Object IsDownLoad) {
      this.IsDownLoad = IsDownLoad;
    }
  }
}
