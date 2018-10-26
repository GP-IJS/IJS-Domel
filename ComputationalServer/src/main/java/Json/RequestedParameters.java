package Json;

public class RequestedParameters
{
    public double P138_Hub_BladeInletDiameter;
    public double P142_SHROUD_BladeInletDiameter;
    public double P145_SideHUB_R1;
    public double P146_SideHUB_R2;
    public double P147_SideHUB_M_Height;
    public double P151_SideSHROUD_R1;
    public double P152_SideSHROUD_R2;
    public double P149_CK_OUT_Height;
    public double P209_SideSHROUD_MID_height_change;
    public double P153_HUB_Blade_InletAngle;
    public double P154_HUB_Blade_OutletAngle;
    public double P159_HUB_Blade_MidAng;
    public double P31_SHROUD_BladeInletAngleAddon;
    public double P32_SHROUD_BladeOutletAngleAddon;
    public double P160_SHROUD_Blade_MidAng;
    public double P30_BLADE_Number;
    public long ScriptTimeout;

    @Override
    public String toString()
    {
        return "\"P138-Hub_BladeInletDiameter\" " + this.P138_Hub_BladeInletDiameter + " [mm]" + System.lineSeparator() +
                "\"P142-SHROUD_BladeInletDiameter\" " + this.P142_SHROUD_BladeInletDiameter + " [mm]" + System.lineSeparator() +
                "\"P145-SideHUB_R1\" " + this.P145_SideHUB_R1 + " [mm]"+ System.lineSeparator() +
                "\"P146-SideHUB_R2\" " + this.P146_SideHUB_R2 + " [mm]"+ System.lineSeparator() +
                "\"P147-SideHUB_M_Height\" " + this.P147_SideHUB_M_Height + " [mm]"+ System.lineSeparator() +
                "\"P151-SideSHROUD_R1\" " + this.P151_SideSHROUD_R1 + " [mm]"+ System.lineSeparator() +
                "\"P152-SideSHROUD_R2\" " + this.P152_SideSHROUD_R2 + " [mm]"+ System.lineSeparator() +
                "\"P149-CK_OUT_Height\" " + this.P149_CK_OUT_Height + " [mm]"+ System.lineSeparator() +
                "\"P209-SideSHROUD_MID_height_change\" " + this.P209_SideSHROUD_MID_height_change + " [mm]"+ System.lineSeparator() +
                "\"P153-HUB_Blade_InletAngle\" " + this.P153_HUB_Blade_InletAngle + " [degree]"+ System.lineSeparator() +
                "\"P154-HUB_Blade_OutletAngle\" " + this.P154_HUB_Blade_OutletAngle + " [degree]"+ System.lineSeparator() +
                "\"P159-HUB_Blade_MidAng\" " + this.P159_HUB_Blade_MidAng + " [mm]"+ System.lineSeparator() +
                "\"P31-SHROUD_BladeInletAngleAddon\" " + this.P31_SHROUD_BladeInletAngleAddon + " [degree]"+ System.lineSeparator() +
                "\"P32-SHROUD_BladeOutletAngleAddon\" " + this.P32_SHROUD_BladeOutletAngleAddon + " [degree]"+ System.lineSeparator() +
                "\"P160-SHROUD_Blade_MidAng\" " + this.P160_SHROUD_Blade_MidAng + " [mm]"+ System.lineSeparator() +
                "\"P30-BLADE_Number\" " + (int) Math.round(this.P30_BLADE_Number) + " [/]";
    }
}

