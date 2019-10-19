/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Memoria;
import modelo.Proceso;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Tita
 */
public class Operaciones {
    
    public void altaMemoria(Memoria memoria, ArrayList<Proceso> listaprocesos){
        
        SessionFactory sesion = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sesion.openSession();
        Transaction tx = session.beginTransaction();
        session.save(memoria);
        for (Proceso proceso : listaprocesos){
        proceso.setMemoria(memoria);
        session.save(proceso);
        }
        tx.commit();
        session.close();
        JOptionPane.showMessageDialog(null,"Insertado Correctamente");
        
        
    }
    
    public void actualizarMemoria(Integer idm, String alg1, String alg2){
        Memoria memoria = new Memoria();
        SessionFactory sesion = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sesion.openSession();
        Transaction tx = session.beginTransaction();
        memoria = (Memoria)session.get(Memoria.class, idm);
        memoria.setAlgasigmemo(alg1);
        memoria.setAlgplanproc(alg2);
        session.update(memoria);
        tx.commit();
        session.close();
        JOptionPane.showMessageDialog(null,"Actualizado Correctamente");
    
    }
    
    public void actualizarMemoriaRR(Integer idm, String alg1, String alg2, int quantum){
        Memoria memoria = new Memoria();
        SessionFactory sesion = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sesion.openSession();
        Transaction tx = session.beginTransaction();
        memoria = (Memoria)session.get(Memoria.class, idm);
        memoria.setAlgasigmemo(alg1);
        memoria.setAlgplanproc(alg2);
        memoria.setQuantum(quantum);
        session.update(memoria);
        tx.commit();
        session.close();
        JOptionPane.showMessageDialog(null,"Actualizado Correctamente");
    
    }
    
    
    
    public Memoria buscarMemoria(Integer idm){
        Memoria memoria = new Memoria();
        SessionFactory sesion = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sesion.openSession();
        Transaction tx = session.beginTransaction();
        memoria = (Memoria)session.get(Memoria.class, idm);
        tx.commit();
        session.close();
        return memoria;
    }
    
    public ArrayList<Proceso> buscarProcesos(Integer idm){
        SessionFactory sesion = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sesion.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("FROM Proceso WHERE memoria.idmemoria = :idm");
        query.setParameter("idm", idm);
        ArrayList<Proceso> listp = (ArrayList<Proceso>)query.list();
        tx.commit();
        session.close();
        return listp; 
        
    }
    
    public ArrayList<Memoria> listarMemorias(){
        SessionFactory sesion = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sesion.openSession();
        Transaction tx = session.beginTransaction();
        ArrayList<Memoria> listm = (ArrayList<Memoria>)session.createQuery("FROM Memoria").list();
        tx.commit();
        session.close();
        return listm;
        
    }
    
}
