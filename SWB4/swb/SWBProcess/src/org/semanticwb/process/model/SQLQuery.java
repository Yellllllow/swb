package org.semanticwb.process.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SQLQuery extends org.semanticwb.process.model.base.SQLQueryBase {

    private Logger log = SWBUtils.getLogger(SQLQuery.class);

    public SQLQuery(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user) {
        super.execute(instance, user);

        String query = getQuery();
        DBConnection dbconn = getDbConnection();

        Document dom = SWBUtils.XML.getNewDocument();
        Element node = dom.createElement("resultset");
        dom.appendChild(node);
        if (query != null && query.trim().length() > 0 && dbconn != null) {
            try {
                Connection con = DriverManager.getConnection(dbconn.getUrl(), dbconn.getUser(), dbconn.getPassword());

                Statement st = con.createStatement();
                int affectedRows = 0;
                if (query.toLowerCase().startsWith("delete") || query.toLowerCase().startsWith("insert") || query.toLowerCase().startsWith("update") || query.toLowerCase().startsWith("drop") || query.toLowerCase().startsWith("alter") || query.toLowerCase().startsWith("create")) //
                {
                    affectedRows = st.executeUpdate(query);
                    Element nodeRow = dom.createElement("row");
                    node.appendChild(nodeRow);
                    Element nodeColumn = dom.createElement("column");
                    nodeColumn.setAttribute("name", "Registros afectados");
                    nodeColumn.setAttribute("type", "integer");
                    nodeColumn.setNodeValue("" + affectedRows);
                    nodeRow.appendChild(nodeColumn);
                } else {
                    ResultSet rs = st.executeQuery(query);
                    try {
                        ResultSetMetaData md = rs.getMetaData();
                        int col = md.getColumnCount();
                        while (rs.next()) {
                            Element nodeRow = dom.createElement("row");
                            node.appendChild(nodeRow);
                            for (int x = 1; x <= col; x++) {
                                Element nodeColumn = dom.createElement("column");
                                String aux = rs.getString(x);
                                if (aux == null) {
                                    aux = "";
                                }
                                nodeColumn.setAttribute("name", md.getColumnName(x));
                                nodeColumn.setAttribute("type", md.getColumnTypeName(x));
                                if (aux.indexOf("<?xml") > -1) {
                                    aux = SWBUtils.XML.replaceXMLChars(aux);
                                }
                                nodeColumn.setNodeValue(aux);
                                nodeRow.appendChild(nodeColumn);
                            }
                        }
                    } catch (Exception e) {
                        log.error("Error en expresion SQL.", e);
                    }
                    rs.close();
                }
                st.close();
                con.close();
            } catch (Exception e) {
                log.error("Error al generar la conección a la Base de Datos.", e);
            }
        } else {

            log.error("Error de configuración, falta definir query ó configuración de conección a la Base de Datos.");
        }
        if (dom != null) {
            System.out.println("XML: --------");
            System.out.println(SWBUtils.XML.domToXml(dom));
        }
    }
}
