package org.semanticwb.portal.community;

import java.net.URLEncoder;


public class ProductElement extends org.semanticwb.portal.community.base.ProductElementBase 
{
    public ProductElement(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    @Override
    public String getURL()
    {
        String url = "#";
        url=this.getWebPage().getUrl();
        return url;
        //url+="?&act=detail&uri="+URLEncoder.encode(this.getURI());
    }
}
