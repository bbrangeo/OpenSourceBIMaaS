//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.03.13 at 04:20:51 PM CET 
//


package org.buildingsmart_tech.mvdxml.mvdxml1_1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ModelView complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ModelView">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BaseView" type="{http://buildingsmart-tech.org/mvdXML/mvdXML1-1}GenericReference" minOccurs="0"/>
 *         &lt;element name="Definitions" type="{http://buildingsmart-tech.org/mvdXML/mvdXML1-1}Definitions" minOccurs="0"/>
 *         &lt;element name="ExchangeRequirements" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://buildingsmart-tech.org/mvdXML/mvdXML1-1}ExchangeRequirement" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Roots" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://buildingsmart-tech.org/mvdXML/mvdXML1-1}ConceptRoot" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://buildingsmart-tech.org/mvdXML/mvdXML1-1}identity"/>
 *       &lt;attribute name="applicableSchema" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModelView", propOrder = {
    "baseView",
    "definitions",
    "exchangeRequirements",
    "roots"
})
public class ModelView {

    @XmlElement(name = "BaseView")
    protected GenericReference baseView;
    @XmlElement(name = "Definitions")
    protected Definitions definitions;
    @XmlElement(name = "ExchangeRequirements")
    protected ModelView.ExchangeRequirements exchangeRequirements;
    @XmlElement(name = "Roots")
    protected ModelView.Roots roots;
    @XmlAttribute(name = "applicableSchema", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String applicableSchema;
    @XmlAttribute(name = "uuid", required = true)
    protected String uuid;
    @XmlAttribute(name = "name", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String name;
    @XmlAttribute(name = "code")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String code;
    @XmlAttribute(name = "version")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String version;
    @XmlAttribute(name = "status")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String status;
    @XmlAttribute(name = "author")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String author;
    @XmlAttribute(name = "owner")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String owner;
    @XmlAttribute(name = "copyright")
    @XmlSchemaType(name = "anyURI")
    protected String copyright;

    /**
     * Gets the value of the baseView property.
     * 
     * @return
     *     possible object is
     *     {@link GenericReference }
     *     
     */
    public GenericReference getBaseView() {
        return baseView;
    }

    /**
     * Sets the value of the baseView property.
     * 
     * @param value
     *     allowed object is
     *     {@link GenericReference }
     *     
     */
    public void setBaseView(GenericReference value) {
        this.baseView = value;
    }

    /**
     * Gets the value of the definitions property.
     * 
     * @return
     *     possible object is
     *     {@link Definitions }
     *     
     */
    public Definitions getDefinitions() {
        return definitions;
    }

    /**
     * Sets the value of the definitions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Definitions }
     *     
     */
    public void setDefinitions(Definitions value) {
        this.definitions = value;
    }

    /**
     * Gets the value of the exchangeRequirements property.
     * 
     * @return
     *     possible object is
     *     {@link ModelView.ExchangeRequirements }
     *     
     */
    public ModelView.ExchangeRequirements getExchangeRequirements() {
        return exchangeRequirements;
    }

    /**
     * Sets the value of the exchangeRequirements property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModelView.ExchangeRequirements }
     *     
     */
    public void setExchangeRequirements(ModelView.ExchangeRequirements value) {
        this.exchangeRequirements = value;
    }

    /**
     * Gets the value of the roots property.
     * 
     * @return
     *     possible object is
     *     {@link ModelView.Roots }
     *     
     */
    public ModelView.Roots getRoots() {
        return roots;
    }

    /**
     * Sets the value of the roots property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModelView.Roots }
     *     
     */
    public void setRoots(ModelView.Roots value) {
        this.roots = value;
    }

    /**
     * Gets the value of the applicableSchema property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicableSchema() {
        return applicableSchema;
    }

    /**
     * Sets the value of the applicableSchema property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicableSchema(String value) {
        this.applicableSchema = value;
    }

    /**
     * Gets the value of the uuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the value of the uuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUuid(String value) {
        this.uuid = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the author property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the value of the author property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthor(String value) {
        this.author = value;
    }

    /**
     * Gets the value of the owner property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the value of the owner property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwner(String value) {
        this.owner = value;
    }

    /**
     * Gets the value of the copyright property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * Sets the value of the copyright property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCopyright(String value) {
        this.copyright = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{http://buildingsmart-tech.org/mvdXML/mvdXML1-1}ExchangeRequirement" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "exchangeRequirement"
    })
    public static class ExchangeRequirements {

        @XmlElement(name = "ExchangeRequirement", required = true)
        protected List<ExchangeRequirement> exchangeRequirement;

        /**
         * The ExchangeRequirement element is the description of an Exchange Requirement model (ERM) that is covered by the MVD. An ERM can be referenced from a Concept element to impose specific constraints for exchanges that reference this ERM. Gets the value of the exchangeRequirement property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the exchangeRequirement property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getExchangeRequirement().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ExchangeRequirement }
         * 
         * 
         */
        public List<ExchangeRequirement> getExchangeRequirement() {
            if (exchangeRequirement == null) {
                exchangeRequirement = new ArrayList<ExchangeRequirement>();
            }
            return this.exchangeRequirement;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{http://buildingsmart-tech.org/mvdXML/mvdXML1-1}ConceptRoot" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "conceptRoot"
    })
    public static class Roots {

        @XmlElement(name = "ConceptRoot", required = true)
        protected List<ConceptRoot> conceptRoot;

        /**
         * The ConceptRoot element represents the root element (other terms are "leaf node class", "variable concept") that represent the fundamental parts of an MVD that is represented by a collection of supported concepts. It has an applicable leaf-node IFC entity. Gets the value of the conceptRoot property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the conceptRoot property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getConceptRoot().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ConceptRoot }
         * 
         * 
         */
        public List<ConceptRoot> getConceptRoot() {
            if (conceptRoot == null) {
                conceptRoot = new ArrayList<ConceptRoot>();
            }
            return this.conceptRoot;
        }

    }

}
