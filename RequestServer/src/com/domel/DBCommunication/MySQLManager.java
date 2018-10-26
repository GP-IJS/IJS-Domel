package com.domel.DBCommunication;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.domel.Entity.*;
import com.domel.Util.ResourceLoader;

import javafx.util.Pair;

public class MySQLManager implements IDBManager
{

    String url;
    String user;
    String password;
    Logger LOGGER;

    public MySQLManager(String url, String username, String password, Logger LOGGER)
    {
    	this.url = url;
    	this.user = username;
    	this.password = password;
    	this.LOGGER = LOGGER;
    	
    	try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		}
    }

    private Result ResultSetToResultMapper(ResultSet rs) throws SQLException
    {
        Result r = new Result();
        r.resultId = UUID.fromString(rs.getString("ResponseID"));
        r.P68_InputPower_CK = rs.getDouble("P68_InputPower_CK");
        r.P66_AirPower_CK_TS = rs.getDouble("P66_AirPower_CK_TS");
        r.P69_Efficiency_CK_TS = rs.getDouble("P69_Efficiency_CK_TS");
        r.P71_PressureRise_CK = rs.getDouble("P71_PressureRise_CK");
        r.P72_LossCoefficiennt_CK = rs.getDouble("P72_LossCoefficiennt_CK");
        r.P214_IsentropicEffic_CK_TT = rs.getDouble("P214_IsentropicEffic_CK_TT");
        r.P218_CK_Press_delta_TT = rs.getDouble("P218_CK_Press_delta_TT");
        r.P186_TurbKinE = rs.getDouble("P186_TurbKinE");
        return r;
    }
    
    private Request RequestToResultMapper(ResultSet rs) throws SQLException
    {
    	Request r = new Request();
    	r.requestID = UUID.fromString(rs.getString("RequestID"));
    	r.status = rs.getString("Status");
    	r.userID = UUID.fromString(rs.getString("UserID"));
    	r.requestParametersID = UUID.fromString(rs.getString("RequestParametersID"));
    	if(!(rs.getString("ResponseDataID")==null || rs.getString("ResponseDataID").isEmpty()))
    		r.responseDataID = UUID.fromString(rs.getString("ResponseDataID"));
    	r.setFullRequestJSON(rs.getString("FullRequestJSON"));
    	r.arrivalDate=rs.getTimestamp("ArrivalDate");
    	r.startedCalculationDate=rs.getTimestamp("StartedCalculationDate");
    	r.finishedCalculationDate=rs.getTimestamp("FinishedCalculationDate");
        return r;
    }

    private RequestParameters ResultSetToRequestParametersMapper(ResultSet rs) throws SQLException
    {
        RequestParameters p = new RequestParameters(ResourceLoader.getFieldsNameMapperInstance().intFields());
        p.parametersId = UUID.fromString(rs.getString("ParametersID"));
        p.p138HubBladeInletDiameter = rs.getDouble("P138_Hub_BladeInletDiameter");
        p.p142ShroudBladeInletDiameter = rs.getDouble("P142_SHROUD_BladeInletDiameter");
        p.p145SideHubR1 = rs.getDouble("P145_SideHUB_R1");
        p.p146SideHubR2 = rs.getDouble("P146_SideHUB_R2");
        p.p147SideHubMHeight = rs.getDouble("P147_SideHUB_M_Height");
        p.p151SideShroudR1 = rs.getDouble("P151_SideSHROUD_R1");
        p.p152SideShroudR2 = rs.getDouble("P152_SideSHROUD_R2");
        p.p149CkOutHeight = rs.getDouble("P149_CK_OUT_Height");
        p.p209SideShroudMidHeightChange = rs.getDouble("P209_SideSHROUD_MID_height_change");
        p.p153HubBladeInletAngle = rs.getDouble("P153_HUB_Blade_InletAngle");
        p.p154HubBladeOutletAngle = rs.getDouble("P154_HUB_Blade_OutletAngle");
        p.p159HubBladeMidAng = rs.getDouble("P159_HUB_Blade_MidAng");
        p.p31ShroudBladeInletAngleAddon = rs.getDouble("P31_SHROUD_BladeInletAngleAddon");
        p.p32ShroudBladeOutletAngleAddon = rs.getDouble("P32_SHROUD_BladeOutletAngleAddon");
        p.p160ShroudBladeMidAng = rs.getDouble("P160_SHROUD_Blade_MidAng");
        p.p30BladeNumber = rs.getInt("P30_BLADE_Number");
        p.ProcessingTime = (Integer) rs.getObject("Processing_Time");
        return p;
    }


    @Override
    public void insertRequestParams(RequestParameters par)
    {
        try(Connection conn = DriverManager.getConnection(url, user, password))
        {
            String query = "INSERT INTO request_parameters (ParametersID, P138_Hub_BladeInletDiameter, P142_SHROUD_BladeInletDiameter, P145_SideHUB_R1, P146_SideHUB_R2, P147_SideHUB_M_Height, P151_SideSHROUD_R1, P152_SideSHROUD_R2, P149_CK_OUT_Height, P209_SideSHROUD_MID_height_change, P153_HUB_Blade_InletAngle, P154_HUB_Blade_OutletAngle, P159_HUB_Blade_MidAng, P31_SHROUD_BladeInletAngleAddon, P32_SHROUD_BladeOutletAngleAddon, P160_SHROUD_Blade_MidAng, P30_BLADE_Number, Processing_Time) "
            		+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(query);


            st.setString(1, par.parametersId.toString());
            st.setDouble(2, par.p138HubBladeInletDiameter);
            st.setDouble(3, par.p142ShroudBladeInletDiameter);
            st.setDouble(4, par.p145SideHubR1);
            st.setDouble(5, par.p146SideHubR2);
            st.setDouble(6, par.p147SideHubMHeight);
            st.setDouble(7, par.p151SideShroudR1);
            st.setDouble(8, par.p152SideShroudR2);
            st.setDouble(9, par.p149CkOutHeight);
            st.setDouble(10, par.p209SideShroudMidHeightChange);
            st.setDouble(11, par.p153HubBladeInletAngle);
            st.setDouble(12, par.p154HubBladeOutletAngle);
            st.setDouble(13, par.p159HubBladeMidAng);
            st.setDouble(14, par.p31ShroudBladeInletAngleAddon);
            st.setDouble(15, par.p32ShroudBladeOutletAngleAddon);
            st.setDouble(16, par.p160ShroudBladeMidAng);
            st.setDouble(17, par.p30BladeNumber);
            
            if(par.ProcessingTime==null)
            	st.setNull(18, Types.INTEGER);
            else
            	st.setInt(18, par.ProcessingTime);

            st.executeUpdate();
        }
        catch (Exception e)
        {
        	LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public RequestParameters getRequestParams(UUID id)
    {
        try(Connection conn = DriverManager.getConnection(url, user, password))
        {
            String query = "SELECT * FROM request_parameters WHERE ParametersID = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, id.toString());
            
            ResultSet rs = st.executeQuery();

            boolean b = rs.next();
            if(b == false)
                return null;

            RequestParameters p = ResultSetToRequestParametersMapper(rs);
            return p;
        }
        catch (Exception e)
        {
        	LOGGER.log(Level.SEVERE, e.getMessage());
        }

        return null;
    }

    @Override
    public RequestParameters getRequestParams(RequestParameters par)
    {
        try(Connection conn = DriverManager.getConnection(url, user, password))
        {
            String query = "SELECT * FROM request_parameters WHERE " +
                    "P138_Hub_BladeInletDiameter = ? " +
                    "AND P142_SHROUD_BladeInletDiameter = ? " +
                    "AND P145_SideHUB_R1 = ? " +
                    "AND P146_SideHUB_R2 = ? " +
                    "AND P147_SideHUB_M_Height = ? " +
                    "AND P151_SideSHROUD_R1 = ? " +
                    "AND P152_SideSHROUD_R2 = ? " +
                    "AND P149_CK_OUT_Height = ? " +
                    "AND P209_SideSHROUD_MID_height_change = ? " +
                    "AND P153_HUB_Blade_InletAngle = ? " +
                    "AND P154_HUB_Blade_OutletAngle = ? " +
                    "AND P159_HUB_Blade_MidAng = ? " +
                    "AND P31_SHROUD_BladeInletAngleAddon = ? " +
                    "AND P32_SHROUD_BladeOutletAngleAddon = ? " +
                    "AND P160_SHROUD_Blade_MidAng = ? " +
                    "AND P30_BLADE_Number = ? ";

            PreparedStatement st = conn.prepareStatement(query);
            st.setDouble(1, par.p138HubBladeInletDiameter);
            st.setDouble(2, par.p142ShroudBladeInletDiameter);
            st.setDouble(3, par.p145SideHubR1);
            st.setDouble(4, par.p146SideHubR2);
            st.setDouble(5, par.p147SideHubMHeight);
            st.setDouble(6, par.p151SideShroudR1);
            st.setDouble(7, par.p152SideShroudR2);
            st.setDouble(8, par.p149CkOutHeight);
            st.setDouble(9, par.p209SideShroudMidHeightChange);
            st.setDouble(10, par.p153HubBladeInletAngle);
            st.setDouble(11, par.p154HubBladeOutletAngle);
            st.setDouble(12, par.p159HubBladeMidAng);
            st.setDouble(13, par.p31ShroudBladeInletAngleAddon);
            st.setDouble(14, par.p32ShroudBladeOutletAngleAddon);
            st.setDouble(15, par.p160ShroudBladeMidAng);
            st.setDouble(16, par.p30BladeNumber);
            
            ResultSet rs = st.executeQuery();


            boolean b = rs.next();
            if(b == false)
                return null;

            RequestParameters p = ResultSetToRequestParametersMapper(rs);
            return p;

        }
        catch (Exception e)
        {
        	LOGGER.log(Level.SEVERE, e.getMessage());
        }

        return null;
    }

    @Override
    public void insertResult(Result insert)
    {
    	try(Connection conn = DriverManager.getConnection(url, user, password))
    	{
    		String query = "INSERT INTO response_data (ResponseID, P68_InputPower_CK, P66_AirPower_CK_TS, P69_Efficiency_CK_TS, P71_PressureRise_CK, P72_LossCoefficiennt_CK, P214_IsentropicEffic_CK_TT, P218_CK_Press_delta_TT, P186_TurbKinE) "
    				+ "VALUES(?,?,?,?,?,?,?,?,?)";
    		PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, insert.resultId.toString());
            st.setDouble(2, insert.P68_InputPower_CK);
            st.setDouble(3, insert.P66_AirPower_CK_TS);
            st.setDouble(4, insert.P69_Efficiency_CK_TS);
            st.setDouble(5, insert.P71_PressureRise_CK);
            st.setDouble(6, insert.P72_LossCoefficiennt_CK);
            st.setDouble(7, insert.P214_IsentropicEffic_CK_TT);
            st.setDouble(8, insert.P218_CK_Press_delta_TT);
            st.setDouble(9, insert.P186_TurbKinE);
            
            st.executeUpdate();
    	}
    	catch (Exception e)
    	{
    		LOGGER.log(Level.SEVERE, e.getMessage());
		}
    	
    }

    @Override
    public Result getResult(UUID id)
    {
    	if(id==null)
    		return null;
        try(Connection conn = DriverManager.getConnection(url, user, password))
        {
            String query = "SELECT * FROM response_data WHERE ResponseID = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, id.toString());
            
            ResultSet rs = st.executeQuery();

            boolean b = rs.next();
            if(b == false)
                return null;

            Result r = this.ResultSetToResultMapper(rs);
            return r;
        }
        catch (Exception e)
        {
        	LOGGER.log(Level.SEVERE, "No results with UUID " + id.toString() + " in the databse");
        }
        return null;
    }

    @Override
    public User getUser(String username, String password)
    {
        try(Connection conn = DriverManager.getConnection(url, user, this.password))
        {
            String query = "SELECT * FROM application_user WHERE Username = ? AND Password = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();

            boolean b = rs.next();
            if(b == false)
                return null;

            User p = new User();

            p.userId = UUID.fromString(rs.getString("UserID"));
            p.username = rs.getString("Username");
            p.password = rs.getString("Password");
            p.name = rs.getString("Name");
            p.privateAccess = rs.getInt("PrivateAccess");


            return  p;


        }
        catch (Exception e)
        {
        	LOGGER.log(Level.SEVERE, e.getMessage());
        }

        return null;
    }

    @Override
    public void insertRequest(Request par)
    {
        try(Connection conn = DriverManager.getConnection(url, user, password))
        {
            String query = "INSERT INTO request (RequestID, Status, UserID, RequestParametersID, ResponseDataID, ArrivalDate, FinishedCalculationDate, FullRequestJSON, StartedCalculationDate) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(query);

            st.setString(1, par.requestID.toString());
            st.setString(2, par.status);

            if(par.userID!=null)
                st.setString(3, par.userID.toString());
            else
                st.setNull(3, Types.NVARCHAR);

            if(par.requestParametersID!=null)
                st.setString(4, par.requestParametersID.toString());
            else
                st.setNull(4, Types.NVARCHAR);

            if(par.responseDataID!=null)
                st.setString(5, par.responseDataID.toString());
            else
                st.setNull(5, Types.NVARCHAR);

            st.setTimestamp(6, par.arrivalDate);

            if(par.finishedCalculationDate!=null)
                st.setTimestamp(7, par.finishedCalculationDate);
            else
                st.setNull(7, Types.DATE);
            
            if(par.getFullRequestJSON()!=null)
            	st.setString(8, par.getFullRequestJSON());
            else
            	st.setNull(8, Types.NVARCHAR);
            
            if(par.startedCalculationDate!=null)
                st.setTimestamp(9, par.startedCalculationDate);
            else
                st.setNull(9, Types.DATE);

            st.executeUpdate();
        }
        catch (Exception e)
        {
        	LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

	@Override
	public void updateRequest(UUID requestID, UUID responseID, String status, Timestamp processingCompleted)
	{
		try(Connection conn = DriverManager.getConnection(url, user, password))
		{
	        String query = "UPDATE request SET"
	        		+ " Status = ?,"
	        		+ " ResponseDataID = ?,"
	        		+ " FinishedCalculationDate = ?"
	        		+ " WHERE RequestID = ?"
	        		+ " AND ResponseDataID IS NULL ";
	        PreparedStatement st = conn.prepareStatement(query);
	
	        st.setString(1, status);
	        st.setString(2, responseID.toString());
	        st.setTimestamp(3, processingCompleted);
	        st.setString(4, requestID.toString());
	
	        st.executeUpdate();
	    }
	    catch (Exception e)
	    {
	    	LOGGER.log(Level.SEVERE, e.getMessage());
	    }
	}

	@Override
	public Result getResultForExistingRequestParameters(UUID parametersID)
	{
        try(Connection conn = DriverManager.getConnection(url, user, password))
        {
            String query = "SELECT response_data.* FROM request " + 
            		"INNER JOIN response_data ON request.ResponseDataID = response_data.ResponseID " + 
            		"WHERE request.RequestParametersID = ? AND Status LIKE 'Success'";

            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, parametersID.toString());
            
            ResultSet rs = st.executeQuery();

            boolean b = rs.next();
            if(b == false)
                return null;
            
            return this.ResultSetToResultMapper(rs);
        }
    	catch (Exception e)
    	{
    		LOGGER.log(Level.SEVERE, e.getMessage());
    	}

        return null;
	}

	@Override
	public Request getRequest(UUID id)
	{
		try(Connection conn = DriverManager.getConnection(url, user, password))
        {
            String query = "SELECT * FROM request WHERE RequestID = ?";

            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, id.toString());
            
            ResultSet rs = st.executeQuery();

            boolean b = rs.next();
            if(b == false)
                return null;
            
            return this.RequestToResultMapper(rs);
        }
    	catch (Exception e)
    	{
    		LOGGER.log(Level.SEVERE, e.getMessage());
    	}
		
		return null;
	}

	@Override
	public Pair<UUID, RequestParameters> getUnsuccessfulRequest()
	{
		try(Connection conn = DriverManager.getConnection(url, user, password))
        {

            String query = "SELECT * FROM request INNER JOIN "
            		+ " request_parameters on RequestParametersID=ParametersID "
            		+ " WHERE Status LIKE \"NotCalculated\" "
            		+ " OR Status LIKE \"FailedToCreateParametersFile\" "
            		+ " OR Status LIKE \"PreviousRequestStillProcessing\" "
            		+ " OR Status LIKE \"SessionTimeout\" "
            		+ " ORDER BY ArrivalDate LIMIT 1";
            
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery();

            boolean b = rs.next();
            if(b == false)
                return null;
            
            
            RequestParameters p = ResultSetToRequestParametersMapper(rs);
            UUID id = UUID.fromString(rs.getString("RequestID"));
            return new Pair<>(id, p);

        }
    	catch (Exception e)
    	{
    		LOGGER.log(Level.SEVERE, e.getMessage());
    	}
		return null;
	}

	@Override
	public void updateRequestsWithEqualParams(UUID parametersID, UUID responseID, String status, Timestamp processingCompleted, Timestamp processingStarted)
	{
		try(Connection conn = DriverManager.getConnection(url, user, password))
		{
	        String query = "UPDATE request SET Status = ?, ResponseDataID = ?, FinishedCalculationDate = ?, StartedCalculationDate=? WHERE RequestParametersID = ?";
	        PreparedStatement st = conn.prepareStatement(query);
	
	        st.setString(1, status);
	        st.setString(2, responseID.toString());
	        st.setTimestamp(3, processingCompleted);
	        st.setTimestamp(4, processingStarted);
	        st.setString(5, parametersID.toString());
	        
	
	        st.executeUpdate();
	    }
	    catch (Exception e)
	    {
	    	LOGGER.log(Level.SEVERE, e.getMessage());
	    }
	}

	@Override
	public void updateFailedRequest(UUID requestID, String status)
	{
		try(Connection conn = DriverManager.getConnection(url, user, password))
		{
	        String query = "UPDATE request SET Status = ? WHERE RequestID = ?";
	        PreparedStatement st = conn.prepareStatement(query);
	
	        st.setString(1, status);
	        st.setString(2, requestID.toString());
	
	        st.executeUpdate();
	    }
	    catch (Exception e)
	    {
	    	LOGGER.log(Level.SEVERE, e.getMessage());
	    }
		
	}
	
	@Override
	public List<Request> allRequests()
	{
		try(Connection conn = DriverManager.getConnection(url, user, password))
        {
            String query = "SELECT * FROM request";

            PreparedStatement st = conn.prepareStatement(query);
            
            ResultSet rs = st.executeQuery();
            List<Request> ls = new ArrayList<Request>();
            
            while(rs.next())
            {
            	ls.add(RequestToResultMapper(rs));
            }
            
            return ls;
        }
    	catch (Exception e)
    	{
    		LOGGER.log(Level.SEVERE, e.getMessage());
    	}
		return null;
	}

	@Override
	public List<Request> userRequests(UUID userId)
	{
		try(Connection conn = DriverManager.getConnection(url, user, password))
        {
            String query = "SELECT * FROM request WHERE UserID = ?";

            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, userId.toString());
            
            ResultSet rs = st.executeQuery();
            List<Request> ls = new ArrayList<Request>();
            
            while(rs.next())
            {
            	ls.add(RequestToResultMapper(rs));
            }
            
            return ls;
        }
    	catch (Exception e)
    	{
    		LOGGER.log(Level.SEVERE, e.getMessage());
    	}
		return null;
	}

	@Override
	public int getNumberofUnsuccessfulRequest()
	{
		try(Connection conn = DriverManager.getConnection(url, user, password))
        {
            String query = "SELECT COUNT(*) FROM request INNER JOIN "
            		+ " request_parameters on RequestParametersID=ParametersID "
            		+ " WHERE Status like \"NotCalculated\" "
            		+ " OR Status LIKE \"FailedToCreateParametersFile\" "
            		+ " OR Status LIKE \"SessionTimeout\" "
            		+ " OR Status LIKE \"PreviousRequestStillProcessing\" ";
            
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery();

            boolean b = rs.next();
            if(b == false)
                return -1;
            return rs.getInt(1);
        }
    	catch (Exception e)
    	{
    		LOGGER.log(Level.SEVERE, e.getMessage());
    	}
		return -1;
	}

	@Override
	public void updateRequestsWithFailedStatus(UUID parametersID, String status, Timestamp processingCompleted, Timestamp processingStarted)
	{
		try(Connection conn = DriverManager.getConnection(url, user, password))
		{
			//System.out.println("test");
	        String query = "UPDATE request SET Status = ?, FinishedCalculationDate = ?, StartedCalculationDate=? WHERE RequestParametersID = ?";
	        PreparedStatement st = conn.prepareStatement(query);
	        
	        
	
	        st.setString(1, status);
	        st.setTimestamp(2, processingCompleted);
	        st.setTimestamp(3, processingStarted);
	        st.setString(4, parametersID.toString());
	
	        //System.out.println(query.toString());
	        
	        st.executeUpdate();
	    }
	    catch (Exception e)
	    {
	    	//e.printStackTrace();
	    	LOGGER.log(Level.SEVERE, e.getMessage());
	    }
		
	}

	@Override
	public int getCurrentQueueOrder(UUID requestId)
	{
		try(Connection conn = DriverManager.getConnection(url, user, password))
        {
            String query = "SELECT * FROM request "
            		+ " WHERE Status like \"NotCalculated\" "
            		+ " OR Status LIKE \"FailedToCreateParametersFile\" "
            		+ " OR Status LIKE \"SessionTimeout\" "
            		+ " OR Status LIKE \"PreviousRequestStillProcessing\" ";

            PreparedStatement st = conn.prepareStatement(query);
            //st.setString(1, userId.toString());
            
            ResultSet rs = st.executeQuery();
            //List<Request> ls = new ArrayList<Request>();
            
            int counter = 0;
            while(rs.next())
            {
            	//ls.add(RequestToResultMapper(rs));
            	Request r = RequestToResultMapper(rs);
            	if(r.requestID.equals(requestId))
            		return counter;
            	counter++;
            	
            }
            
            return -1;
        }
    	catch (Exception e)
    	{
    		LOGGER.log(Level.SEVERE, e.getMessage());
    	}
		return -1;
	}

	@Override
	public void calculationStarted(UUID RequestId, Timestamp time)
	{
		try(Connection conn = DriverManager.getConnection(url, user, password))
		{
	        String query = "UPDATE request SET"
	        		+ " StartedCalculationDate = ? WHERE RequestId=?";// AND StartedCalculationDate IS NULL";
	        PreparedStatement st = conn.prepareStatement(query);
	
	        st.setTimestamp(1, time);
	        st.setString(2, RequestId.toString());
	        st.executeUpdate();
	    }
	    catch (Exception e)
	    {
	    	LOGGER.log(Level.SEVERE, e.getMessage());
	    }
		
	}

	@Override
	public void setExecutionTime(UUID parametersID, int time)
	{
		try(Connection conn = DriverManager.getConnection(url, user, password))
		{
	        String query = "UPDATE request_parameters SET"
	        		+ " Processing_Time = ?"
	        		+ " WHERE ParametersID = ?";
	        PreparedStatement st = conn.prepareStatement(query);
	
	        st.setInt(1, time);
	        st.setString(2, parametersID.toString());
	
	        st.executeUpdate();
	    }
	    catch (Exception e)
	    {
	    	LOGGER.log(Level.SEVERE, e.getMessage());
	    }
		
	}

	@Override
	public Map<String, Object> getStatus() {
		try(Connection conn = DriverManager.getConnection(url, user, password))
		{
			Map<String, Object> map = new HashMap<String, Object>();

	        String query = "select Status, count(*) as 'Count' from request group by Status;";
	        PreparedStatement st = conn.prepareStatement(query);
	        
	        ResultSet rs = st.executeQuery();
	        while (rs.next())
	        {
	        	String status = rs.getString("Status");
	        	int count = rs.getInt("Count");
	        	map.put(status, count);
	        }
	        return map;
	    }
	    catch (Exception e)
	    {
	    	LOGGER.log(Level.SEVERE, e.getMessage());
	    }
		return new HashMap<String, Object>();
	}
}


