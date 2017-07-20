import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jayway.jsonpath.*;
//import net.minidev.json.*;

/**
 * Servlet implementation class retrieveTeamInfo
 */
@WebServlet("/retrieveTeamInfo")
public class retrieveTeamInfo extends HttpServlet {
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String output = "";
		String outputJSON = "";
		try {
			//fixed URL link with team ID appended at the end 
	        URL url = new URL("https://cis2017-teamtracker.herokuapp.com/api/teams/iMqa_BJ0HpsgVfJKxOJDGQ");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
	 
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
	
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			
			if ((output = br.readLine()) != null) {
				outputJSON += output;
				System.out.println(output);
//				String cash = JsonPath.read(outputJSON, "$.cash");
//		        System.out.println(cash);
				String holdingList3988 = JsonPath.read(output, "$.3988");
				System.out.println(holdingList3988);
				String reserveList3988 = JsonPath.read(output, "$.3988_reserved");
				System.out.println(reserveList3988);
				
				String holdingList0001 = JsonPath.read(output, "$.0001");
				String reserveList0001= JsonPath.read(output, "$.0001_reserved");
				
				String holdingList0388 = JsonPath.read(output, "$.0388");
				String reserveList0388= JsonPath.read(output, "$.0388_reserved");
				
				String holdingList0005 = JsonPath.read(output, "$.0005");
				String reserveList0005= JsonPath.read(output, "$.0005_reserved");
				
				String holdingList0386 = JsonPath.read(output, "$.0386");
				String reserveList0386 = JsonPath.read(output, "$.0386_reserved");
				
				//rounding off 2 decimal places for cash and profits
				String cashRaw = JsonPath.read(output, "$.cash");
				double cash = (Math.round(Double.parseDouble(cashRaw) * 100.0) / 100.0);
				String cashReserveRaw = JsonPath.read(output, "$.reserved_cash");
				double cashReserve = (Math.round(Double.parseDouble(cashReserveRaw) * 100.0) / 100.0);
				//calculating profit based on initial sum of 1M 
				double profitRaw = Double.parseDouble(cashRaw) - 1000000;
				String profit = (Math.round(profitRaw * 100.0) / 100.0) + "" ;
				
				request.setAttribute("cash", "" + cash);
				request.setAttribute("cashReserve", "" + cashReserve);
				request.setAttribute("profit", profit);
				
				request.setAttribute("holdingList3988", holdingList3988);
				request.setAttribute("reserveList3988", reserveList3988);
				
				request.setAttribute("holdingList0001", holdingList0001);
				request.setAttribute("reserveList0001", reserveList0001);
				
				request.setAttribute("holdingList0388", holdingList0388);
				request.setAttribute("reserveList0388", reserveList0388);
				
				request.setAttribute("holdingList0005", holdingList0005);
				request.setAttribute("reserveList0005", reserveList0005);
				
				request.setAttribute("holdingList0386", holdingList0386);
				request.setAttribute("reserveList0386", reserveList0386);
			}
			
        }catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
        RequestDispatcher view = request.getRequestDispatcher("index.jsp");
        view.forward(request, response); 
	}

}
