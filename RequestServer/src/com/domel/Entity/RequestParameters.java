package com.domel.Entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RequestParameters
{
	private static Gson gson;
	List<String> toIntFields;
	
	public RequestParameters(List<String> toIntFields)
	{
		this.toIntFields = toIntFields;
		gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
	}
	
	@Expose(serialize = false)
    public UUID parametersId;
	@Expose
    @SerializedName("P138_Hub_BladeInletDiameter")
    public double p138HubBladeInletDiameter;
	@Expose
    @SerializedName("P142_SHROUD_BladeInletDiameter")
    public double p142ShroudBladeInletDiameter;
	@Expose
    @SerializedName("P145_SideHUB_R1")
    public double p145SideHubR1;
	@Expose
    @SerializedName("P146_SideHUB_R2")
    public double p146SideHubR2;
	@Expose
    @SerializedName("P147_SideHUB_M_Height")
    public double p147SideHubMHeight;
	@Expose
    @SerializedName("P151_SideSHROUD_R1")
    public double p151SideShroudR1;
	@Expose
    @SerializedName("P152_SideSHROUD_R2")
    public double p152SideShroudR2;
	@Expose
    @SerializedName("P149_CK_OUT_Height")
    public double p149CkOutHeight;
	@Expose
    @SerializedName("P209_SideSHROUD_MID_height_change")
    public double p209SideShroudMidHeightChange;
	@Expose
    @SerializedName("P153_HUB_Blade_InletAngle")
    public double p153HubBladeInletAngle;
	@Expose
    @SerializedName("P154_HUB_Blade_OutletAngle")
    public double p154HubBladeOutletAngle;
	@Expose
    @SerializedName("P159_HUB_Blade_MidAng")
    public double p159HubBladeMidAng;
	@Expose
    @SerializedName("P31_SHROUD_BladeInletAngleAddon")
    public double p31ShroudBladeInletAngleAddon;
	@Expose
    @SerializedName("P32_SHROUD_BladeOutletAngleAddon")
    public double p32ShroudBladeOutletAngleAddon;
	@Expose
    @SerializedName("P160_SHROUD_Blade_MidAng")
    public double p160ShroudBladeMidAng;
	@Expose
    @SerializedName("P30_BLADE_Number")
    public int p30BladeNumber;
	@Expose
    @SerializedName("ScriptTimeout")
    public long ScriptTimeout;
	@Expose
    @SerializedName("ProcessingTime")
	public Integer ProcessingTime;
	
	@Override
	public String toString()
	{
		String s_json = RequestParameters.gson.toJson(this);
		Map<String,Object> map = new HashMap<String,Object>();
		map = (Map<String,Object>) gson.fromJson(s_json, map.getClass());
		
		for (Map.Entry<String, Object> entry : map.entrySet()) {
		    String key = entry.getKey();
		    Double value = (Double)entry.getValue();
		    if(this.toIntFields.contains(key))
		    {
		    	map.put(key, (int) Math.round(value));
		    }
		}
		return RequestParameters.gson.toJson(map);
	}
}
