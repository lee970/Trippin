package server;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Geocode {
	private String latitude;
	private String longitude;
	
	public String destinationName;
	//just use geo.la to get latitude and geo.lo to get longitude
	//AFTER calling parseLatLon
	public Double la;
	public Double lo;
	//WILL BE TRUE IF IT'S VALID, WILL BE FALSE IF INVALID LOCATTION
	
	public Geocode() {
		//constructor
	}
	public boolean parseLatLon(String placeNames) {
		//NOTE: THE FORMAT HAS TO BE CORRECT- NO SYMBOLS THAT ARE NOT LETTERS, NUMBERS, COMMAS, SPACES.
		//if nothing is found, i think it returns empty string 
		destinationName=  placeNames;
		try {
				String[] values = placeNames.split("\\s+");
				String url = "";
				String base = "http://nominatim.openstreetmap.org/search/";
				String params = "?q=";
				String query = "";
				String params2 = "&format=xml&polygon=1&addressdetails=1";
				for(int i = 0; i < values.length; i++)
				{
					if(i != values.length -1)
					{
						query += values[i] + "+";
					}
					else
					{
						query += values[i];
					}
				}
				
				String charset = "UTF-8";
				url = base+params+query+params2;
				/*URLConnection connection = new URL(url).openConnection();
				connection.setRequestProperty("Accept-Charset", charset);
				InputStream response = connection.getInputStream();*/
				
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(new URL(url).openStream());
		        doc.getDocumentElement().normalize();

		        NodeList nList = doc.getElementsByTagName("place");
		        
		        Node nNode = nList.item(0);
		        if(nNode == null)
		        {
		        	return false;
		        }
		        else if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		               Element eElement = (Element) nNode;
		               latitude = eElement.getAttribute("lat");
		               longitude = eElement.getAttribute("lon");
		               this.la = Double.parseDouble(latitude);
		   			   this.lo = Double.parseDouble(longitude);
		   			   return true;
		        }
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return false; 

				
		}
	
		
	}

