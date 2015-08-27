# BIMaaS Platform

BIMaaS is a single, powerful, flexible open source platform, which facilitates asset data collaboration and governance of BIM standards. BIM as a Service is the only enterprise grade open source platform for asset data integration and BIM governance. Gain complete visibility of all building information management data across your entire business or infrastructure projects, share asset data securely with other departments and third party suppliers - including infrastructure, operational and maintenance - and enforce BIM Level 2 compliance using BIMaaS. BIMaaS is built for asset management executives, buildings and civil managers, large infrastructure project managers and CIOs in sectors such as Rail, Aviation, Utilities, Oil and Gas and Construction.
BIMaaS platform is built on top of WSO2 product stack. BIMServer and BIMSurfer are used to manage the BIM standard and IFC data.
See full features of BIMaaS platform in the BIMaaS official web page [www.bimaas.uk].

#How to build from source
As BIMaaS platform utilises WSO2 middleware stack, a user must initially download the required WSO2 products (IS, AM, MB, ESB, AS, UES and DSS) from [http://wso2.com/products/] and replace the configuration file which can be found in config/{product}/{path-to-each-files} with the corresponding product folder. Consequently the user is required to navigate to dev/ folder and build the entire project using "maven" and place the built artifacts (jar/war) inside each product {product}/repository/components/lib folder.

For more details please visit [https://github.com/MitraInnovationRepo/OpenSourceBIMaaS/wiki]

[https://github.com/MitraInnovationRepo/OpenSourceBIMaaS/wiki]: https://github.com/MitraInnovationRepo/OpenSourceBIMaaS/wiki
[www.bimaas.uk]: http://bimaas.uk
[http://wso2.com/products/]: http://wso2.com/products/
