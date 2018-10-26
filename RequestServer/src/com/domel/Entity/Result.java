package com.domel.Entity;

import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class Result
{
	@Expose(serialize = false)
    public UUID resultId;
	@Expose()
    public double P68_InputPower_CK;
	@Expose()
    public double P66_AirPower_CK_TS;
	@Expose()
    public double P69_Efficiency_CK_TS;
	@Expose()
    public double P71_PressureRise_CK;
	@Expose()
    public double P72_LossCoefficiennt_CK;
	@Expose()
    public double P214_IsentropicEffic_CK_TT;
	@Expose()
    public double P218_CK_Press_delta_TT;
	@Expose()
    public double P186_TurbKinE;
    
	@Override
	public String toString()
	{
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		return gson.toJson(this);	
	}
}
