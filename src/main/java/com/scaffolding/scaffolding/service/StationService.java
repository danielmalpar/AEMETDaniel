package com.scaffolding.scaffolding.service;


import com.scaffolding.scaffolding.model.Station;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class StationService implements IStationService {
    //las variables que son de clase que sean privadas,las de dentro del metodo las dejo asi

    private final String APIKEY = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYWxwYXJoZG1pQGdtYWlsLmNvbSIsImp0aSI6IjdkMTI2MjViLTdjNWYtNDE5YS1iOWNkLTRiNjExNTQ4YjIwNyIsImlzcyI6IkFFTUVUIiwiaWF0IjoxNjUwMzUyNTYzLCJ1c2VySWQiOiI3ZDEyNjI1Yi03YzVmLTQxOWEtYjljZC00YjYxMTU0OGIyMDciLCJyb2xlIjoiIn0.LVQtHeCem6I3bW5oRg5gL9SK5NN8u12PJFyo1WS4yfk";
    private static LocalDate dateNow=LocalDate.now();
    private String date=dateNow.toString()+"T00%3A00%3A00UTC";
   private static String idema="0149X";//0149X para manresa 1111X para santander
   private Logger logger = LoggerFactory.getLogger(StationService.class);
   private int divide=0;//para dividir por las estaciones que tengan datos
   private float total=0;//el total de las medias

   @Override
    public ArrayList<Station> OneStation(String estacion) throws IOException, ParseException {


        LocalDate dateFifteen= dateNow.minusDays(15);
        String date2=dateFifteen+"T00%3A00%3A00UTC";


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://opendata.aemet.es/opendata/api/valores/climatologicos/diarios/datos/fechaini/"+date2+"/fechafin/"+date+"/estacion/"+estacion+"?api_key="+ APIKEY)
                .get()
                .addHeader("cache-control", "no-cache")
                .build();//me ha hecho la peticion del aemet donde me da la url con la request"
        Response response;//creo el Response "

        response = client.newCall(request).execute();
        String responeDataString=response.body().string();//pasamos el body del JSON a string para que podamos
        JSONObject auxiliarJsonObject= new JSONObject(responeDataString);
        String QueryURL=  auxiliarJsonObject.getString("datos");
        OkHttpClient client2 = new OkHttpClient();
        Request request2 = new Request.Builder()
                .url(QueryURL)
                .get()
                .addHeader("cache-control", "no-cache")
                .build();
        //me ha hecho la 2º peticion a aemet donde ya me muestra los json

        Response response2;
        response2 = client2.newCall(request2).execute();
        String responeDataString2=response2.body().string();
        JSONArray jsonArray = new JSONArray(responeDataString2);
        ArrayList<Station> ArrayStations= new ArrayList<>();
        if (jsonArray.length() > 0) {
            Station station;
            for (int i = 0; i < jsonArray.length(); i++) {//hacemos un for para recorrer el JsonArray y meterle en la station que creamos con los valores de temp
                NumberFormat nf = NumberFormat.getInstance(); //para parsear el tipo y formato de dato ya que el json nos devuelve mal el 6,3 como 6.3
                station = new Station();
                JSONObject jsonObjectLine = (JSONObject) jsonArray.get(i);;

                if(jsonObjectLine.has("tmed")&&jsonObjectLine.has("tmax")&&jsonObjectLine.has("tmin")) {
                    String StationName = jsonObjectLine.getString("nombre");
                    logger.info("Estacion de "+StationName);
                    String StationDate = jsonObjectLine.getString("fecha");
                    logger.info("Fecha de "+StationDate);
                    String dateTmed = jsonObjectLine.getString("tmed");
                    logger.info("temperatura media "+dateTmed);
                    String dateTmax = jsonObjectLine.getString("tmax");
                    logger.info("temperatura maxima "+dateTmax);
                    String dateTmin = jsonObjectLine.getString("tmin");
                    logger.info("temperatura minima "+dateTmin);

                    station.setTmed((float) (nf.parse((jsonObjectLine.get("tmed").toString()))).doubleValue());//parseamos la temperatura media a float
                    station.setTmax((float) nf.parse((jsonObjectLine.get("tmax").toString())).doubleValue());
                    station.setTmin((float) nf.parse((jsonObjectLine.get("tmin").toString())).doubleValue());
                    ArrayStations.add(station);

                    Station stationForTemps=new Station();



                    for (i= 0; i < ArrayStations.size(); i++) {
                        if (ArrayStations.get(i).getTmax() > stationForTemps.getTmax())
                            stationForTemps=ArrayStations.get(i);

                    }
                    logger.info("Maximum temperatura = " + stationForTemps.getTmax());

                    for (i= 0; i < ArrayStations.size(); i++) {
                        if (ArrayStations.get(i).getTmin() < stationForTemps.getTmin())
                            stationForTemps=ArrayStations.get(i);

                    }
                    logger.info("Minimum temperatura = " + stationForTemps.getTmin());

                    for (i= 0; i < ArrayStations.size(); i++) {
                        if (ArrayStations.get(i).getTmed() > stationForTemps.getTmed())
                            stationForTemps=ArrayStations.get(i);
                        divide=divide+i;
                        total=total+ArrayStations.get(i).getTmed();

                    }
                    logger.info("Med temperatura total= " +(total/divide));
                    //importante tiene que ser la suna de todas las medias y hacer la media
                    // hacerlo con el i del for mas las temperaturas medias las parseas,las sumas y la divides por los dias


                }
                else{
                    String StationName = jsonObjectLine.getString("nombre");
                    String StationDate = jsonObjectLine.getString("fecha");
                    logger.info(date);
                    logger.info("***********************NO TIENE DATOS  LA ESTACION "+(StationName)+" DEL DIA "+(StationDate)+" ***********************");
                }
            }

        } else {
            logger.info("el array está vacio");
        }
        return ArrayStations;

    }

    public void AllStations() throws ParseException, IOException {
        LocalDate dateFifteen= dateNow.minusDays(15);
        String date2=dateFifteen+"T00%3A00%3A00UTC";


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://opendata.aemet.es/opendata/api/valores/climatologicos/diarios/datos/fechaini/"+date2+"/fechafin/"+date+"/todasestaciones?api_key=" + APIKEY)
                .get()
                .addHeader("cache-control", "no-cache")
                .build();
        Response response;

        response = client.newCall(request).execute();
        String responeDataString=response.body().string();//pasamos el body del JSON a string para que podamos
        System.out.println(response.toString());

        JSONObject auxiliarJsonObject= new JSONObject(responeDataString);

        String QueryURL=  auxiliarJsonObject.getString("datos");


        OkHttpClient client2 = new OkHttpClient();
        Request request2 = new Request.Builder()
                .url(QueryURL)
                .get()
                .addHeader("cache-control", "no-cache")
                .build();
        System.out.println("me ha hecho la peticion a aemet ");
        Response response2;

        System.out.println("creo el Response response");
        response2 = client2.newCall(request2).execute();
        String responeDataString2=response2.body().string();

        JSONArray jsonArray = new JSONArray(responeDataString2);
        ArrayList<Station> ArrayStations;
        if (jsonArray.length() > 0) {
            Station station;
            ArrayStations = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                NumberFormat nf = NumberFormat.getInstance();

                station = new Station();

                JSONObject jsonObjectLine = (JSONObject) jsonArray.get(i);;




                if(jsonObjectLine.has("tmed")&&jsonObjectLine.has("tmax")&&jsonObjectLine.has("tmin")) {
                    String StationName = jsonObjectLine.getString("nombre");
                    System.out.println("Estacion de "+StationName);
                    String StationDate = jsonObjectLine.getString("fecha");
                    System.out.println("Fecha de "+StationDate);
                    String dateTmed = jsonObjectLine.getString("tmed");
                    System.out.println("temperatura media "+dateTmed);
                    String dateTmax = jsonObjectLine.getString("tmax");
                    System.out.println("temperatura maxima "+dateTmax);
                    String dateTmin = jsonObjectLine.getString("tmin");
                    System.out.println("temperatura minima "+dateTmin);

                    station.setTmed((float) (nf.parse((jsonObjectLine.get("tmed").toString()))).doubleValue());
                    station.setTmax((float) nf.parse((jsonObjectLine.get("tmax").toString())).doubleValue());
                    station.setTmin((float) nf.parse((jsonObjectLine.get("tmin").toString())).doubleValue());
                    ArrayStations.add(station);
                    Station stationForTemps=new Station();

                    for (i= 0; i < ArrayStations.size(); i++) {
                        if (ArrayStations.get(i).getTmax() > stationForTemps.getTmax())
                            stationForTemps=ArrayStations.get(i);

                    }
                    logger.info("Maximum temperatura = " + stationForTemps.getTmax());

                    for (i= 0; i < ArrayStations.size(); i++) {
                        if (ArrayStations.get(i).getTmin() < stationForTemps.getTmin())
                            stationForTemps=ArrayStations.get(i);

                    }
                    logger.info("Minimum temperatura = " + stationForTemps.getTmin());

                    for (i= 0; i < ArrayStations.size(); i++) {
                        if (ArrayStations.get(i).getTmed() > stationForTemps.getTmed())
                            stationForTemps=ArrayStations.get(i);
                        divide=divide+i;
                        total=total+ArrayStations.get(i).getTmed();

                    }

                }
                else{
                    String StationName = jsonObjectLine.getString("nombre");
                    String StationDate = jsonObjectLine.getString("fecha");
                    System.out.println(date);
                    System.out.println("***********************NO TIENE DATOS  LA ESTACION "+(StationName)+" DEL DIA "+(StationDate)+" ***********************");
                }
            }

        } else {
            System.out.println("el array está vacio");
        }

    }








}

