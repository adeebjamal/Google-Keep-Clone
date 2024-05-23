package com.example.GoogleKeepClone.entities;

public class OtpVerificationRequest {

    private Integer OTP;

    public Integer getOTP() {
        // get OTP
        return this.OTP;
    }

    public void setOTP(Integer OTP) {
        // set OTP
        this.OTP = OTP;
    }

    @Override
    public String toString() {
        return "OtpVerificationRequest { OTP = " + this.OTP + " }";
    }

}
