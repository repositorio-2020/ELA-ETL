/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.mybatis.pojos;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

/**
 *
 * @author David Lara 
 * @version 20191030
 * @see pojo para el almacenamiento de Calendario
 * 
 */

public class MdlLogStore implements Serializable  {

    
    private String program;
    private long id;
    private String eventname;
    private String component;
    private String logaction;
    private String target;
    private String objecttable;
    private long objectid;
    private String crud;
    private long edulevel;
    private long contextid;
    private long contextlevel;
    private long contextinstanceid;
    private long userid;
    private long courseid;
    private long relateduserid;
    private long anonymous;
    private String other;   // Este es Vacio
    private Date timecreated;
    private String origin;
    private String ip;
    private long realuserid;

    public MdlLogStore() {
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getLogaction() {
        return logaction;
    }

    public void setLogaction(String logaction) {
        this.logaction = logaction;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getObjecttable() {
        return objecttable;
    }

    public void setObjecttable(String objecttable) {
        this.objecttable = objecttable;
    }

    public long getObjectid() {
        return objectid;
    }

    public void setObjectid(long objectid) {
        this.objectid = objectid;
    }

    public String getCrud() {
        return crud;
    }

    public void setCrud(String crud) {
        this.crud = crud;
    }

    public long getEdulevel() {
        return edulevel;
    }

    public void setEdulevel(long edulevel) {
        this.edulevel = edulevel;
    }

    public long getContextid() {
        return contextid;
    }

    public void setContextid(long contextid) {
        this.contextid = contextid;
    }

    public long getContextlevel() {
        return contextlevel;
    }

    public void setContextlevel(long contextlevel) {
        this.contextlevel = contextlevel;
    }

    public long getContextinstanceid() {
        return contextinstanceid;
    }

    public void setContextinstanceid(long contextinstanceid) {
        this.contextinstanceid = contextinstanceid;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public long getCourseid() {
        return courseid;
    }

    public void setCourseid(long courseid) {
        this.courseid = courseid;
    }

    public long getRelateduserid() {
        return relateduserid;
    }

    public void setRelateduserid(long relateduserid) {
        this.relateduserid = relateduserid;
    }

    public long getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(long anonymous) {
        this.anonymous = anonymous;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Date getTimecreated() {
        return timecreated;
    }

    public void setTimecreated(Date timecreated) {
        this.timecreated = timecreated;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getRealuserid() {
        return realuserid;
    }

    public void setRealuserid(long realuserid) {
        this.realuserid = realuserid;
    }

   
    
    
    
    
    
    
    public void limpiar() {

        this.program = null;
        this.id = 0;
        this.eventname = null;
             this.component = null;
             this.logaction = null;
             this.target = null;
             this.objecttable = null;
             this.objectid = 0;
             this.crud = null;
             this.edulevel = 0;
             this.contextid = 0;
             this.contextlevel = 0;
             this.contextinstanceid = 0;
             this.userid = 0;
             this.courseid = 0;
             this.relateduserid= 0;
             this.anonymous= 0;
             this.other= null;
             this.timecreated= null;
             this.origin= null;
             this.ip= null;
             this.realuserid= 0;

        
    }   
    
   
    
    
    
}
