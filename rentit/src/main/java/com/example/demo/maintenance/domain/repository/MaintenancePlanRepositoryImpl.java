package com.example.demo.maintenance.domain.repository;

import com.example.demo.common.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;

public class MaintenancePlanRepositoryImpl implements CustomMaintenancePlanRepository {
    @Autowired
    EntityManager em;
/*
    @Override
    public List<Pair<Integer, Long>> findCorrectiveRepairsByYearForPeriod(int year, int thisyear) {
       */
/* Query query = em.createQuery("select new com.example.demo.utils.Pair(mp.yearOfAction,count(corr)) from " +
                "com.example.demo.models.MaintenancePlan mp,MaintenanceTask corr " +
                "where corr = (select corr1 from MaintenanceTask corr1 where " +
                "corr1.plantReservation.maintenancePlan = mp and " +
                "corr1.typeOfWork = com.example.demo.models.enums.TypeOfWork.CORRECTIVE " +
                "and corr1.plantReservation.maintenancePlan.yearOfAction >=2014 and " +
                "corr1.plantReservation.maintenancePlan.yearOfAction <=2018)" +
                "member " +
                "group by mp.yearOfAction");*//*


        Query query = em.createQuery("select new com.example.demo.utils.Pair(mp.yearOfAction,count(corr)) from " +
                "com.example.demo.models.MaintenancePlan mp,MaintenanceTask corr " +
                "where corr member of mp.tasks and " +
                "corr.typeOfWork = com.example.demo.models.enums.TypeOfWork.CORRECTIVE and " +
                "mp.yearOfAction >=(:year) and " +
                "mp.yearOfAction <=(:thisyear)" +
                "group by mp.yearOfAction")
                .setParameter("year",year)
                .setParameter("thisyear",thisyear);


//        TypedQuery<List<Pair>> query =  em.createQuery("select com.example.demo.utils.Pair(mp.yearOfAction,size(mp))" +
//                " from MaintenancePlan mp",Pair.class)
        return (List<Pair<Integer,Long>>)query.getResultList();
    }

*/
    @Override
    public List<Pair<Integer, BigDecimal>> findCorrectiveRepairCostsByYear(int year, int thisyear) {

        Query query = em.createQuery("select new com.example.demo.common.utils.Pair(mp.yearOfAction,sum(mt.price.price)) from " +
                "com.example.demo.maintenance.domain.model.MaintenancePlan mp,MaintenanceTask mt " +
                "where mt member of mp.tasks and " +
                "mt.typeOfWork = com.example.demo.maintenance.domain.model.TypeOfWork.CORRECTIVE and " +
                "mp.yearOfAction >=(:year) and " +
                "mp.yearOfAction <=(:thisyear)" +
                "group by mp.yearOfAction")
                .setParameter("year",year)
                .setParameter("thisyear",thisyear);


        return (List<Pair<Integer,BigDecimal>>)query.getResultList();
    }
}
