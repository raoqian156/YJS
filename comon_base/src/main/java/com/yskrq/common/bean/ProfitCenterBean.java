package com.yskrq.common.bean;

import com.yskrq.net_library.BaseBean;

import java.io.Serializable;
import java.util.List;

public class ProfitCenterBean extends BaseBean {

    /**
     * Account : null
     * No : null
     * IsShowTeaCar : 0
     * ProfitCenter_value : [{"ShopsId":"226725","Code":"000202  ","Description":"足浴中心","GroupCode":"30","ExStatus":"","DefaultSvrRatio":"10","isDefaultSvrRound":"True","isDefaultDiscountRound":"True","MaxRoundValue":"2","WorkMode":"1","RoundKind":"0"},{"ShopsId":"226725","Code":"000205  ","Description":"洗浴中心","GroupCode":"30","ExStatus":"","DefaultSvrRatio":"","isDefaultSvrRound":"","isDefaultDiscountRound":"","MaxRoundValue":"","WorkMode":"1","RoundKind":"0"}]
     * FixingType_value : []
     * FixingView_value : []
     * RelaxInvtTypePCView_value : []
     */

    private Object Account;
    private Object No;
    private int IsShowTeaCar;
    private List<ProfitCenterValueBean> ProfitCenter_value;
    private List<?> FixingType_value;
    private List<?> FixingView_value;
    private List<?> RelaxInvtTypePCView_value;

    public Object getAccount() {
        return Account;
    }

    public void setAccount(Object Account) {
        this.Account = Account;
    }

    public Object getNo() {
        return No;
    }

    public void setNo(Object No) {
        this.No = No;
    }

    public int getIsShowTeaCar() {
        return IsShowTeaCar;
    }

    public void setIsShowTeaCar(int IsShowTeaCar) {
        this.IsShowTeaCar = IsShowTeaCar;
    }

    public List<ProfitCenterValueBean> getProfitCenter_value() {
        return ProfitCenter_value;
    }

    public void setProfitCenter_value(List<ProfitCenterValueBean> ProfitCenter_value) {
        this.ProfitCenter_value = ProfitCenter_value;
    }

    public List<?> getFixingType_value() {
        return FixingType_value;
    }

    public void setFixingType_value(List<?> FixingType_value) {
        this.FixingType_value = FixingType_value;
    }

    public List<?> getFixingView_value() {
        return FixingView_value;
    }

    public void setFixingView_value(List<?> FixingView_value) {
        this.FixingView_value = FixingView_value;
    }

    public List<?> getRelaxInvtTypePCView_value() {
        return RelaxInvtTypePCView_value;
    }

    public void setRelaxInvtTypePCView_value(List<?> RelaxInvtTypePCView_value) {
        this.RelaxInvtTypePCView_value = RelaxInvtTypePCView_value;
    }

    public static class ProfitCenterValueBean implements Serializable {
        /**
         * ShopsId : 226725
         * Code : 000202
         * Description : 足浴中心
         * GroupCode : 30
         * ExStatus :
         * DefaultSvrRatio : 10
         * isDefaultSvrRound : True
         * isDefaultDiscountRound : True
         * MaxRoundValue : 2
         * WorkMode : 1
         * RoundKind : 0
         */

        private String ShopsId;
        private String Code;
        private String Description;
        private String GroupCode;
        private String ExStatus;
        private String DefaultSvrRatio;
        private String isDefaultSvrRound;
        private String isDefaultDiscountRound;
        private String MaxRoundValue;
        private String WorkMode;
        private String RoundKind;

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

        public String getGroupCode() {
            return GroupCode;
        }

        public void setGroupCode(String GroupCode) {
            this.GroupCode = GroupCode;
        }

        public String getExStatus() {
            return ExStatus;
        }

        public void setExStatus(String ExStatus) {
            this.ExStatus = ExStatus;
        }

        public String getDefaultSvrRatio() {
            return DefaultSvrRatio;
        }

        public void setDefaultSvrRatio(String DefaultSvrRatio) {
            this.DefaultSvrRatio = DefaultSvrRatio;
        }

        public String getIsDefaultSvrRound() {
            return isDefaultSvrRound;
        }

        public void setIsDefaultSvrRound(String isDefaultSvrRound) {
            this.isDefaultSvrRound = isDefaultSvrRound;
        }

        public String getIsDefaultDiscountRound() {
            return isDefaultDiscountRound;
        }

        public void setIsDefaultDiscountRound(String isDefaultDiscountRound) {
            this.isDefaultDiscountRound = isDefaultDiscountRound;
        }

        public String getMaxRoundValue() {
            return MaxRoundValue;
        }

        public void setMaxRoundValue(String MaxRoundValue) {
            this.MaxRoundValue = MaxRoundValue;
        }

        public String getWorkMode() {
            return WorkMode;
        }

        public void setWorkMode(String WorkMode) {
            this.WorkMode = WorkMode;
        }

        public String getRoundKind() {
            return RoundKind;
        }

        public void setRoundKind(String RoundKind) {
            this.RoundKind = RoundKind;
        }
    }
}
