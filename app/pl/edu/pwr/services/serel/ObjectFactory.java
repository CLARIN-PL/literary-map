
package pl.edu.pwr.services.serel;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the pl.edu.pwr.services.serel package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SendRequestResponse_QNAME = new QName("http://156.17.134.58/ws/serel/", "SendRequestResponse");
    private final static QName _GetResult_QNAME = new QName("http://156.17.134.58/ws/serel/", "GetResult");
    private final static QName _GetResultJSONResponse_QNAME = new QName("http://156.17.134.58/ws/serel/", "GetResultJSONResponse");
    private final static QName _SendRequest_QNAME = new QName("http://156.17.134.58/ws/serel/", "SendRequest");
    private final static QName _GetResultResponse_QNAME = new QName("http://156.17.134.58/ws/serel/", "GetResultResponse");
    private final static QName _CheckStatus_QNAME = new QName("http://156.17.134.58/ws/serel/", "CheckStatus");
    private final static QName _CheckStatusResponse_QNAME = new QName("http://156.17.134.58/ws/serel/", "CheckStatusResponse");
    private final static QName _GetResultJSON_QNAME = new QName("http://156.17.134.58/ws/serel/", "GetResultJSON");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: pl.edu.pwr.services.serel
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetResultResponseType }
     * 
     */
    public GetResultResponseType createGetResultResponseType() {
        return new GetResultResponseType();
    }

    /**
     * Create an instance of {@link CheckStatusRequestType }
     * 
     */
    public CheckStatusRequestType createCheckStatusRequestType() {
        return new CheckStatusRequestType();
    }

    /**
     * Create an instance of {@link GetResultJSONRequestType }
     * 
     */
    public GetResultJSONRequestType createGetResultJSONRequestType() {
        return new GetResultJSONRequestType();
    }

    /**
     * Create an instance of {@link CheckStatusResponseType }
     * 
     */
    public CheckStatusResponseType createCheckStatusResponseType() {
        return new CheckStatusResponseType();
    }

    /**
     * Create an instance of {@link GetResultJSONResponseType }
     * 
     */
    public GetResultJSONResponseType createGetResultJSONResponseType() {
        return new GetResultJSONResponseType();
    }

    /**
     * Create an instance of {@link GetResultRequestType }
     * 
     */
    public GetResultRequestType createGetResultRequestType() {
        return new GetResultRequestType();
    }

    /**
     * Create an instance of {@link SendRequestResponseType }
     * 
     */
    public SendRequestResponseType createSendRequestResponseType() {
        return new SendRequestResponseType();
    }

    /**
     * Create an instance of {@link SendRequestRequestType }
     * 
     */
    public SendRequestRequestType createSendRequestRequestType() {
        return new SendRequestRequestType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendRequestResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://156.17.134.58/ws/serel/", name = "SendRequestResponse")
    public JAXBElement<SendRequestResponseType> createSendRequestResponse(SendRequestResponseType value) {
        return new JAXBElement<SendRequestResponseType>(_SendRequestResponse_QNAME, SendRequestResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResultRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://156.17.134.58/ws/serel/", name = "GetResult")
    public JAXBElement<GetResultRequestType> createGetResult(GetResultRequestType value) {
        return new JAXBElement<GetResultRequestType>(_GetResult_QNAME, GetResultRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResultJSONResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://156.17.134.58/ws/serel/", name = "GetResultJSONResponse")
    public JAXBElement<GetResultJSONResponseType> createGetResultJSONResponse(GetResultJSONResponseType value) {
        return new JAXBElement<GetResultJSONResponseType>(_GetResultJSONResponse_QNAME, GetResultJSONResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendRequestRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://156.17.134.58/ws/serel/", name = "SendRequest")
    public JAXBElement<SendRequestRequestType> createSendRequest(SendRequestRequestType value) {
        return new JAXBElement<SendRequestRequestType>(_SendRequest_QNAME, SendRequestRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResultResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://156.17.134.58/ws/serel/", name = "GetResultResponse")
    public JAXBElement<GetResultResponseType> createGetResultResponse(GetResultResponseType value) {
        return new JAXBElement<GetResultResponseType>(_GetResultResponse_QNAME, GetResultResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckStatusRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://156.17.134.58/ws/serel/", name = "CheckStatus")
    public JAXBElement<CheckStatusRequestType> createCheckStatus(CheckStatusRequestType value) {
        return new JAXBElement<CheckStatusRequestType>(_CheckStatus_QNAME, CheckStatusRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckStatusResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://156.17.134.58/ws/serel/", name = "CheckStatusResponse")
    public JAXBElement<CheckStatusResponseType> createCheckStatusResponse(CheckStatusResponseType value) {
        return new JAXBElement<CheckStatusResponseType>(_CheckStatusResponse_QNAME, CheckStatusResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResultJSONRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://156.17.134.58/ws/serel/", name = "GetResultJSON")
    public JAXBElement<GetResultJSONRequestType> createGetResultJSON(GetResultJSONRequestType value) {
        return new JAXBElement<GetResultJSONRequestType>(_GetResultJSON_QNAME, GetResultJSONRequestType.class, null, value);
    }

}
