package org.semanticwb.model;

import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticVocabulary;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;
import static org.semanticwb.platform.SemanticVocabulary.URI;

public class SWBVocabulary
{


    //Classes
    public final SemanticClass User;
    public final SemanticClass Calendar;
    public final SemanticClass Text;
    public final SemanticClass Community;
    public final SemanticClass PortletSubType;
    public final SemanticClass TemplateRef;
    public final SemanticClass Templateable;
    public final SemanticClass Deleteable;
    public final SemanticClass Reference;
    public final SemanticClass Roleable;
    public final SemanticClass Traceable;
    public final SemanticClass RoleRefable;
    public final SemanticClass Ruleable;
    public final SemanticClass TextArea;
    public final SemanticClass PFlow;
    public final SemanticClass IPFilter;
    public final SemanticClass RuleRefable;
    public final SemanticClass Valueable;
    public final SemanticClass Calendarable;
    public final SemanticClass PortletRefable;
    public final SemanticClass DisplayObject;
    public final SemanticClass Permission;
    public final SemanticClass TemplateRefable;
    public final SemanticClass PFlowRef;
    public final SemanticClass RuleRef;
    public final SemanticClass Referensable;
    public final SemanticClass Device;
    public final SemanticClass Hiddenable;
    public final SemanticClass Groupable;
    public final SemanticClass Localeable;
    public final SemanticClass Camp;
    public final SemanticClass Dns;
    public final SemanticClass TemplateGroup;
    public final SemanticClass UserRepository;
    public final SemanticClass Portletable;
    public final SemanticClass Template;
    public final SemanticClass Role;
    public final SemanticClass Priorityable;
    public final SemanticClass Activeable;
    public final SemanticClass VersionInfo;
    public final SemanticClass Portlet;
    public final SemanticClass Descriptiveable;
    public final SemanticClass Versionable;
    public final SemanticClass RoleRef;
    public final SemanticClass XMLable;
    public final SemanticClass Rule;
    public final SemanticClass XMLConfable;
    public final SemanticClass WebPage;
    public final SemanticClass WebPageable;
    public final SemanticClass WebSite;
    public final SemanticClass ObjectGroup;
    public final SemanticClass Language;
    public final SemanticClass PortletRef;
    public final SemanticClass Indexable;
    public final SemanticClass PortletType;
    public final SemanticClass Viewable;



    //Properties
    public final SemanticProperty created;
    public final SemanticProperty usrSecondLastName;
    public final SemanticProperty modifiedBy;
    public final SemanticProperty usrEmail;
    public final SemanticProperty updated;
    public final SemanticProperty usrFirstName;
    public final SemanticProperty usrLanguage;
    public final SemanticProperty usrPasswordChanged;
    public final SemanticProperty usrLastName;
    public final SemanticProperty hasGroup;
    public final SemanticProperty active;
    public final SemanticProperty usrLastLogin;
    public final SemanticProperty creator;
    public final SemanticProperty usrPassword;
    public final SemanticProperty usrLogin;
    public final SemanticProperty hasRole;
    public final SemanticProperty usrSecurityQuestion;
    public final SemanticProperty usrSecurityAnswer;
    public final SemanticProperty title;
    public final SemanticProperty description;
    public final SemanticProperty hasPSTPortlets;
    public final SemanticProperty PSTType;
    public final SemanticProperty deleted;
    public final SemanticProperty template;
    public final SemanticProperty priority;
    public final SemanticProperty hasRoleRef;
    public final SemanticProperty hasRule;
    public final SemanticProperty frmTextAreaRows;
    public final SemanticProperty actualVersion;
    public final SemanticProperty xml;
    public final SemanticProperty lastVersion;
    public final SemanticProperty ipFilterAction;
    public final SemanticProperty ipFilterNumber;
    public final SemanticProperty hasRuleRef;
    public final SemanticProperty value;
    public final SemanticProperty hasCalendar;
    public final SemanticProperty hasPortletRef;
    public final SemanticProperty propGroup;
    public final SemanticProperty viewElement;
    public final SemanticProperty propIndex;
    public final SemanticProperty createElement;
    public final SemanticProperty editElement;
    public final SemanticProperty hasTemplateRef;
    public final SemanticProperty pflow;
    public final SemanticProperty rule;
    public final SemanticProperty hidden;
    public final SemanticProperty language;
    public final SemanticProperty webPage;
    public final SemanticProperty dnsDefault;
    public final SemanticProperty hasGroupedTemplate;
    public final SemanticProperty extendedAttributes;
    public final SemanticProperty hasPortlet;
    public final SemanticProperty templateGroup;
    public final SemanticProperty hasPermission;
    public final SemanticProperty previousVersion;
    public final SemanticProperty versionComment;
    public final SemanticProperty versionFile;
    public final SemanticProperty nextVersion;
    public final SemanticProperty versionNumber;
    public final SemanticProperty camp;
    public final SemanticProperty hits;
    public final SemanticProperty indexable;
    public final SemanticProperty portletWindow;
    public final SemanticProperty xmlConf;
    public final SemanticProperty views;
    public final SemanticProperty portletSubType;
    public final SemanticProperty portletType;
    public final SemanticProperty role;
    public final SemanticProperty webPageSortName;
    public final SemanticProperty hasWebPageVirtualParent;
    public final SemanticProperty hasWebPageVirtualChild;
    public final SemanticProperty hasPFlowRef;
    public final SemanticProperty hasWebPageChild;
    public final SemanticProperty webPageParent;
    public final SemanticProperty homePage;
    public final SemanticProperty userRepository;
    public final SemanticProperty portlet;
    public final SemanticProperty portletBundle;
    public final SemanticProperty portletCache;
    public final SemanticProperty portletClassName;
    public final SemanticProperty hasPTPortlet;
    public final SemanticProperty portletMode;
    public final SemanticProperty hasPTSubType;


    public SWBVocabulary()
    {

         SemanticVocabulary vocabulary=SWBPlatform.getSemanticMgr().getVocabulary();
        // Classes
        User=vocabulary.getSemanticClass(URI+"User");
        Calendar=vocabulary.getSemanticClass(URI+"Calendar");
        Text=vocabulary.getSemanticClass(URI+"Text");
        Community=vocabulary.getSemanticClass(URI+"Community");
        PortletSubType=vocabulary.getSemanticClass(URI+"PortletSubType");
        TemplateRef=vocabulary.getSemanticClass(URI+"TemplateRef");
        Templateable=vocabulary.getSemanticClass(URI+"Templateable");
        Deleteable=vocabulary.getSemanticClass(URI+"Deleteable");
        Reference=vocabulary.getSemanticClass(URI+"Reference");
        Roleable=vocabulary.getSemanticClass(URI+"Roleable");
        Traceable=vocabulary.getSemanticClass(URI+"Traceable");
        RoleRefable=vocabulary.getSemanticClass(URI+"RoleRefable");
        Ruleable=vocabulary.getSemanticClass(URI+"Ruleable");
        TextArea=vocabulary.getSemanticClass(URI+"TextArea");
        PFlow=vocabulary.getSemanticClass(URI+"PFlow");
        IPFilter=vocabulary.getSemanticClass(URI+"IPFilter");
        RuleRefable=vocabulary.getSemanticClass(URI+"RuleRefable");
        Valueable=vocabulary.getSemanticClass(URI+"Valueable");
        Calendarable=vocabulary.getSemanticClass(URI+"Calendarable");
        PortletRefable=vocabulary.getSemanticClass(URI+"PortletRefable");
        DisplayObject=vocabulary.getSemanticClass(URI+"DisplayObject");
        Permission=vocabulary.getSemanticClass(URI+"Permission");
        TemplateRefable=vocabulary.getSemanticClass(URI+"TemplateRefable");
        PFlowRef=vocabulary.getSemanticClass(URI+"PFlowRef");
        RuleRef=vocabulary.getSemanticClass(URI+"RuleRef");
        Referensable=vocabulary.getSemanticClass(URI+"Referensable");
        Device=vocabulary.getSemanticClass(URI+"Device");
        Hiddenable=vocabulary.getSemanticClass(URI+"Hiddenable");
        Groupable=vocabulary.getSemanticClass(URI+"Groupable");
        Localeable=vocabulary.getSemanticClass(URI+"Localeable");
        Camp=vocabulary.getSemanticClass(URI+"Camp");
        Dns=vocabulary.getSemanticClass(URI+"Dns");
        TemplateGroup=vocabulary.getSemanticClass(URI+"TemplateGroup");
        UserRepository=vocabulary.getSemanticClass(URI+"UserRepository");
        Portletable=vocabulary.getSemanticClass(URI+"Portletable");
        Template=vocabulary.getSemanticClass(URI+"Template");
        Role=vocabulary.getSemanticClass(URI+"Role");
        Priorityable=vocabulary.getSemanticClass(URI+"Priorityable");
        Activeable=vocabulary.getSemanticClass(URI+"Activeable");
        VersionInfo=vocabulary.getSemanticClass(URI+"VersionInfo");
        Portlet=vocabulary.getSemanticClass(URI+"Portlet");
        Descriptiveable=vocabulary.getSemanticClass(URI+"Descriptiveable");
        Versionable=vocabulary.getSemanticClass(URI+"Versionable");
        RoleRef=vocabulary.getSemanticClass(URI+"RoleRef");
        XMLable=vocabulary.getSemanticClass(URI+"XMLable");
        Rule=vocabulary.getSemanticClass(URI+"Rule");
        XMLConfable=vocabulary.getSemanticClass(URI+"XMLConfable");
        WebPage=vocabulary.getSemanticClass(URI+"WebPage");
        WebPageable=vocabulary.getSemanticClass(URI+"WebPageable");
        WebSite=vocabulary.getSemanticClass(URI+"WebSite");
        ObjectGroup=vocabulary.getSemanticClass(URI+"ObjectGroup");
        Language=vocabulary.getSemanticClass(URI+"Language");
        PortletRef=vocabulary.getSemanticClass(URI+"PortletRef");
        Indexable=vocabulary.getSemanticClass(URI+"Indexable");
        PortletType=vocabulary.getSemanticClass(URI+"PortletType");
        Viewable=vocabulary.getSemanticClass(URI+"Viewable");



        //Properties
        created=vocabulary.getSemanticProperty(URI+"created");
        usrSecondLastName=vocabulary.getSemanticProperty(URI+"usrSecondLastName");
        modifiedBy=vocabulary.getSemanticProperty(URI+"modifiedBy");
        usrEmail=vocabulary.getSemanticProperty(URI+"usrEmail");
        updated=vocabulary.getSemanticProperty(URI+"updated");
        usrFirstName=vocabulary.getSemanticProperty(URI+"usrFirstName");
        usrLanguage=vocabulary.getSemanticProperty(URI+"usrLanguage");
        usrPasswordChanged=vocabulary.getSemanticProperty(URI+"usrPasswordChanged");
        usrLastName=vocabulary.getSemanticProperty(URI+"usrLastName");
        hasGroup=vocabulary.getSemanticProperty(URI+"hasGroup");
        active=vocabulary.getSemanticProperty(URI+"active");
        usrLastLogin=vocabulary.getSemanticProperty(URI+"usrLastLogin");
        creator=vocabulary.getSemanticProperty(URI+"creator");
        usrPassword=vocabulary.getSemanticProperty(URI+"usrPassword");
        usrLogin=vocabulary.getSemanticProperty(URI+"usrLogin");
        hasRole=vocabulary.getSemanticProperty(URI+"hasRole");
        usrSecurityQuestion=vocabulary.getSemanticProperty(URI+"usrSecurityQuestion");
        usrSecurityAnswer=vocabulary.getSemanticProperty(URI+"usrSecurityAnswer");
        title=vocabulary.getSemanticProperty(URI+"title");
        description=vocabulary.getSemanticProperty(URI+"description");
        hasPSTPortlets=vocabulary.getSemanticProperty(URI+"hasPSTPortlets");
        PSTType=vocabulary.getSemanticProperty(URI+"PSTType");
        deleted=vocabulary.getSemanticProperty(URI+"deleted");
        template=vocabulary.getSemanticProperty(URI+"template");
        priority=vocabulary.getSemanticProperty(URI+"priority");
        hasRoleRef=vocabulary.getSemanticProperty(URI+"hasRoleRef");
        hasRule=vocabulary.getSemanticProperty(URI+"hasRule");
        frmTextAreaRows=vocabulary.getSemanticProperty(URI+"frmTextAreaRows");
        actualVersion=vocabulary.getSemanticProperty(URI+"actualVersion");
        xml=vocabulary.getSemanticProperty(URI+"xml");
        lastVersion=vocabulary.getSemanticProperty(URI+"lastVersion");
        ipFilterAction=vocabulary.getSemanticProperty(URI+"ipFilterAction");
        ipFilterNumber=vocabulary.getSemanticProperty(URI+"ipFilterNumber");
        hasRuleRef=vocabulary.getSemanticProperty(URI+"hasRuleRef");
        value=vocabulary.getSemanticProperty(URI+"value");
        hasCalendar=vocabulary.getSemanticProperty(URI+"hasCalendar");
        hasPortletRef=vocabulary.getSemanticProperty(URI+"hasPortletRef");
        propGroup=vocabulary.getSemanticProperty(URI+"propGroup");
        viewElement=vocabulary.getSemanticProperty(URI+"viewElement");
        propIndex=vocabulary.getSemanticProperty(URI+"propIndex");
        createElement=vocabulary.getSemanticProperty(URI+"createElement");
        editElement=vocabulary.getSemanticProperty(URI+"editElement");
        hasTemplateRef=vocabulary.getSemanticProperty(URI+"hasTemplateRef");
        pflow=vocabulary.getSemanticProperty(URI+"pflow");
        rule=vocabulary.getSemanticProperty(URI+"rule");
        hidden=vocabulary.getSemanticProperty(URI+"hidden");
        language=vocabulary.getSemanticProperty(URI+"language");
        webPage=vocabulary.getSemanticProperty(URI+"webPage");
        dnsDefault=vocabulary.getSemanticProperty(URI+"dnsDefault");
        hasGroupedTemplate=vocabulary.getSemanticProperty(URI+"hasGroupedTemplate");
        extendedAttributes=vocabulary.getSemanticProperty(URI+"extendedAttributes");
        hasPortlet=vocabulary.getSemanticProperty(URI+"hasPortlet");
        templateGroup=vocabulary.getSemanticProperty(URI+"templateGroup");
        hasPermission=vocabulary.getSemanticProperty(URI+"hasPermission");
        previousVersion=vocabulary.getSemanticProperty(URI+"previousVersion");
        versionComment=vocabulary.getSemanticProperty(URI+"versionComment");
        versionFile=vocabulary.getSemanticProperty(URI+"versionFile");
        nextVersion=vocabulary.getSemanticProperty(URI+"nextVersion");
        versionNumber=vocabulary.getSemanticProperty(URI+"versionNumber");
        camp=vocabulary.getSemanticProperty(URI+"camp");
        hits=vocabulary.getSemanticProperty(URI+"hits");
        indexable=vocabulary.getSemanticProperty(URI+"indexable");
        portletWindow=vocabulary.getSemanticProperty(URI+"portletWindow");
        xmlConf=vocabulary.getSemanticProperty(URI+"xmlConf");
        views=vocabulary.getSemanticProperty(URI+"views");
        portletSubType=vocabulary.getSemanticProperty(URI+"portletSubType");
        portletType=vocabulary.getSemanticProperty(URI+"portletType");
        role=vocabulary.getSemanticProperty(URI+"role");
        webPageSortName=vocabulary.getSemanticProperty(URI+"webPageSortName");
        hasWebPageVirtualParent=vocabulary.getSemanticProperty(URI+"hasWebPageVirtualParent");
        hasWebPageVirtualChild=vocabulary.getSemanticProperty(URI+"hasWebPageVirtualChild");
        hasPFlowRef=vocabulary.getSemanticProperty(URI+"hasPFlowRef");
        hasWebPageChild=vocabulary.getSemanticProperty(URI+"hasWebPageChild");
        webPageParent=vocabulary.getSemanticProperty(URI+"webPageParent");
        homePage=vocabulary.getSemanticProperty(URI+"homePage");
        userRepository=vocabulary.getSemanticProperty(URI+"userRepository");
        portlet=vocabulary.getSemanticProperty(URI+"portlet");
        portletBundle=vocabulary.getSemanticProperty(URI+"portletBundle");
        portletCache=vocabulary.getSemanticProperty(URI+"portletCache");
        portletClassName=vocabulary.getSemanticProperty(URI+"portletClassName");
        hasPTPortlet=vocabulary.getSemanticProperty(URI+"hasPTPortlet");
        portletMode=vocabulary.getSemanticProperty(URI+"portletMode");
        hasPTSubType=vocabulary.getSemanticProperty(URI+"hasPTSubType");
    }
}
