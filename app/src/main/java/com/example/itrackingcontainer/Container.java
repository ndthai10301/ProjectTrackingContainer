package com.example.itrackingcontainer;

import java.io.Serializable;

public class Container implements Serializable {
    //Khai bao cac thuoc tinh
    private String ID,CntrNo,CntrClass,Status,BLNo,SealNo,SealNo1,OprID,DateIn,DateOut;
    private String cBlock,cBay,cRow,cTier,cArea,CJMode_CD,ImVoy,ExVoy,POD,FPOD;
    private String CMStatus,ShipID,ShipYear,ShipVoy,CMDWeight,LocalSZPT;
    //Ham khoi tao constructor
    public Container()
    {}

    public Container(String ID, String cntrNo, String cntrClass, String status, String BLNo, String sealNo,
                     String sealNo1, String oprID, String dateIn, String dateOut, String cBlock, String cBay,
                     String cRow, String cTier, String cArea, String CJMode_CD, String imVoy, String exVoy,
                     String POD, String FPOD, String CMStatus, String shipID, String shipYear, String shipVoy,
                     String CMDWeight, String localSZPT) {
        this.ID = ID;
        CntrNo = cntrNo;
        CntrClass = cntrClass;
        Status = status;
        this.BLNo = BLNo;
        SealNo = sealNo;
        SealNo1 = sealNo1;
        OprID = oprID;
        DateIn = dateIn;
        DateOut = dateOut;
        this.cBlock = cBlock;
        this.cBay = cBay;
        this.cRow = cRow;
        this.cTier = cTier;
        this.cArea = cArea;
        this.CJMode_CD = CJMode_CD;
        ImVoy = imVoy;
        ExVoy = exVoy;
        this.POD = POD;
        this.FPOD = FPOD;
        this.CMStatus = CMStatus;
        ShipID = shipID;
        ShipYear = shipYear;
        ShipVoy = shipVoy;
        this.CMDWeight = CMDWeight;
        LocalSZPT = localSZPT;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCntrNo() {
        return CntrNo;
    }

    public void setCntrNo(String cntrNo) {
        CntrNo = cntrNo;
    }

    public String getCntrClass() {
        return CntrClass;
    }

    public void setCntrClass(String cntrClass) {
        CntrClass = cntrClass;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getBLNo() {
        return BLNo;
    }

    public void setBLNo(String BLNo) {
        this.BLNo = BLNo;
    }

    public String getSealNo() {
        return SealNo;
    }

    public void setSealNo(String sealNo) {
        SealNo = sealNo;
    }

    public String getSealNo1() {
        return SealNo1;
    }

    public void setSealNo1(String sealNo1) {
        SealNo1 = sealNo1;
    }

    public String getOprID() {
        return OprID;
    }

    public void setOprID(String oprID) {
        OprID = oprID;
    }

    public String getDateIn() {
        return DateIn;
    }

    public void setDateIn(String dateIn) {
        DateIn = dateIn;
    }

    public String getDateOut() {
        return DateOut;
    }

    public void setDateOut(String dateOut) {
        DateOut = dateOut;
    }

    public String getcBlock() {
        return cBlock;
    }

    public void setcBlock(String cBlock) {
        this.cBlock = cBlock;
    }

    public String getcBay() {
        return cBay;
    }

    public void setcBay(String cBay) {
        this.cBay = cBay;
    }

    public String getcRow() {
        return cRow;
    }

    public void setcRow(String cRow) {
        this.cRow = cRow;
    }

    public String getcTier() {
        return cTier;
    }

    public void setcTier(String cTier) {
        this.cTier = cTier;
    }

    public String getcArea() {
        return cArea;
    }

    public void setcArea(String cArea) {
        this.cArea = cArea;
    }

    public String getCJMode_CD() {
        return CJMode_CD;
    }

    public void setCJMode_CD(String CJMode_CD) {
        this.CJMode_CD = CJMode_CD;
    }

    public String getImVoy() {
        return ImVoy;
    }

    public void setImVoy(String imVoy) {
        ImVoy = imVoy;
    }

    public String getExVoy() {
        return ExVoy;
    }

    public void setExVoy(String exVoy) {
        ExVoy = exVoy;
    }

    public String getPOD() {
        return POD;
    }

    public void setPOD(String POD) {
        this.POD = POD;
    }

    public String getFPOD() {
        return FPOD;
    }

    public void setFPOD(String FPOD) {
        this.FPOD = FPOD;
    }

    public String getCMStatus() {
        return CMStatus;
    }

    public void setCMStatus(String CMStatus) {
        this.CMStatus = CMStatus;
    }

    public String getShipID() {
        return ShipID;
    }

    public void setShipID(String shipID) {
        ShipID = shipID;
    }

    public String getShipYear() {
        return ShipYear;
    }

    public void setShipYear(String shipYear) {
        ShipYear = shipYear;
    }

    public String getShipVoy() {
        return ShipVoy;
    }

    public void setShipVoy(String shipVoy) {
        ShipVoy = shipVoy;
    }

    public String getCMDWeight() {
        return CMDWeight;
    }

    public void setCMDWeight(String CMDWeight) {
        this.CMDWeight = CMDWeight;
    }

    public String getLocalSZPT() {
        return LocalSZPT;
    }

    public void setLocalSZPT(String localSZPT) {
        LocalSZPT = localSZPT;
    }
}
