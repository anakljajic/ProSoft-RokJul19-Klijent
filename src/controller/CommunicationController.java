/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domain.Gazdinstvo;
import domain.Poljoprivrednik;
import domain.Stado;
import domain.Zivotinja;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import transfer.RequestObject;
import transfer.ResponseObject;
import util.Operation;

/**
 *
 * @author student1
 */
public class CommunicationController {

    private static CommunicationController instance;
    private Socket socket;
    private List<Stado> stada;

    private CommunicationController() throws IOException {
        socket = new Socket("localhost", 9000);
        stada = new ArrayList<>();
    }

    public static CommunicationController getInstance() throws IOException {
        if (instance == null) {
            instance = new CommunicationController();
        }
        return instance;
    }

//    public User logIn(String username, String password) throws IOException, ClassNotFoundException, Exception {
//        RequestObject request=new RequestObject();
//        request.setOperation(Operation.OPERATION_LOGIN);
//        
//        Map<String, String> data=new HashMap<>();
//        data.put("username", username);
//        data.put("password", password);
//        request.setData(data);
//        
//        sendRequest(request);
//        ResponseObject response=receiveResponse();
//        
//        if(response.getException()!=null){
//            throw response.getException();
//        }
//        return (User)response.getData();
//    }
//    
//    public List<Manufacturer> getAllManufacturers() throws Exception{
//        RequestObject request=new RequestObject();
//        request.setOperation(Operation.OPERATION_GET_ALL_MANUFACTURERS);
//        
//        sendRequest(request);
//        ResponseObject response=receiveResponse();
//        
//        if(response.getException()!=null){
//            throw response.getException();
//        }
//        return (List<Manufacturer>)response.getData();
//    }
//    
//    public void saveProduct(Product product) throws Exception{
//        RequestObject request=new RequestObject();
//        request.setOperation(Operation.OPERATION_SAVE_PRODUCT);
//        
//        request.setData(product);
//        
//        sendRequest(request);
//        ResponseObject response=receiveResponse();
//        
//        if(response.getException()!=null){
//            throw response.getException();
//        }
//        
//    }
//    
//    public List<Product> getAllProducts() throws Exception{
//        RequestObject request=new RequestObject();
//        request.setOperation(Operation.OPERATION_GET_ALL_PRODUCTS);
//        
//        sendRequest(request);
//        ResponseObject response=receiveResponse();
//        
//        if(response.getException()!=null){
//            throw response.getException();
//        }
//        return (List<Product>)response.getData();
//    }
//    
//    public Invoice saveInvoice(Invoice invoice) throws Exception{
//        RequestObject request=new RequestObject();
//        request.setOperation(Operation.OPERATION_SAVE_INVOICE);
//        
//        request.setData(invoice);
//        
//        sendRequest(request);
//        ResponseObject response=receiveResponse();
//        
//        if(response.getException()!=null){
//            throw response.getException();
//        }
//        return (Invoice)response.getData();
//    }
    private void sendRequest(RequestObject request) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(request);
        out.flush();
    }

    private ResponseObject receiveResponse() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ResponseObject response = (ResponseObject) in.readObject();
        return response;
    }

    public Poljoprivrednik logIn(String username, String password) throws IOException, ClassNotFoundException, Exception {
        RequestObject request = new RequestObject();
        request.setOperation(Operation.OPERATION_LOGIN);

        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);
        request.setData(data);

        sendRequest(request);
        ResponseObject response = receiveResponse();

        if (response.getException() != null) {
            throw response.getException();
        }
        return (Poljoprivrednik) response.getData();
    }

    public List<Poljoprivrednik> getAllPoljoprivrednici() throws Exception {
        RequestObject request = new RequestObject();
        request.setOperation(Operation.OPERATION_GET_ALL_POLJOPRIVREDNICI);

        sendRequest(request);
        ResponseObject response = receiveResponse();

        if (response.getException() != null) {
            throw response.getException();
        }
        return (List<Poljoprivrednik>) response.getData();
    }

    public List<Zivotinja> getAllZivotinje() throws Exception {
        RequestObject request = new RequestObject();
        request.setOperation(Operation.OPERATION_GET_ALL_ZIVOTINJE);

        sendRequest(request);
        ResponseObject response = receiveResponse();

        if (response.getException() != null) {
            throw response.getException();
        }
        return (List<Zivotinja>) response.getData();
    }

    public List<Gazdinstvo> getAllGazdinstva() throws Exception {
        RequestObject request = new RequestObject();
        request.setOperation(Operation.OPERATION_GET_ALL_GAZDINSTVA);

        sendRequest(request);
        ResponseObject response = receiveResponse();

        if (response.getException() != null) {
            throw response.getException();
        }
        return (List<Gazdinstvo>) response.getData();
    }

    public List<Stado> vratiStadaIzMemorije() {
        return stada;
    }

    public void dodajStado(Stado s) {
        stada.add(s);
    }

    public void obrisiStado(int s) {
        stada.remove(s);
    }

    public void saveGazdinstvo(Gazdinstvo g) throws Exception {
        RequestObject request = new RequestObject();
        request.setOperation(Operation.OPERATION_SAVE_GAZDINSTVO);

        request.setData(g);

        sendRequest(request);
        ResponseObject response = receiveResponse();

        if (response.getException() != null) {
            throw response.getException();
        }

    }

    public void saveStada(List<Stado> s) throws Exception {
        RequestObject request = new RequestObject();
        request.setOperation(Operation.OPERATION_SAVE_STADA);

        request.setData(s);

        sendRequest(request);
        ResponseObject response = receiveResponse();

        if (response.getException() != null) {
            throw response.getException();
        }

    }

    public int brojGrlaUStadu(List<Stado> stada) {
        Long brojGrla = 0L;
        for (Stado stado : stada) {
            if (stado.getBrojGrla().equals(brojGrla)) {
                System.out.println("U stadu postoji grlo ciji je broj nula! Greska!");
                return -1;
            }
        }
        return 1;
    }

    public int brojStada(Gazdinstvo g) {
        int brojStada = 0;
        for (Stado stado : stada) {
            if (stado.getGazdinstvo().equals(g)) {
                brojStada++;
            }
        }
        return brojStada;
    }

    public int brojZivotinja() {
        int brojZivotinja = 0;
        Zivotinja z = new Zivotinja();
        z = stada.get(0).getZivotinja();
        for (Stado stado : stada) {
            if (stado.getZivotinja().getNaziv().equals(z.getNaziv())) {
                brojZivotinja++;
            }
        }
        return brojZivotinja;
    }
}
