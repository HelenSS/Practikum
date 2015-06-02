package controller;

import dao.MessageDao;
import dao.MessageDaoImpl;
import model.Message;
import model.MessageStorage;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;
import util.MessageUtil;
import util.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/chat")
public class MessageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(MessageServlet.class.getName());
    private MessageDao messageDao;

    @Override
    public void init() throws ServletException {
        try {
            this.messageDao = new MessageDaoImpl();
            loadHistory();
        } catch (SAXException | IOException | ParserConfigurationException | TransformerException e) {
            logger.error(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("doGet");
        String token = request.getParameter(MessageUtil.TOKEN);
        logger.info("Token " + token);

        try {
            if (token != null && !"".equals(token)) {
                int index = MessageUtil.getIndex(token);
                logger.info("Index " + index);
                String messages;
                messages = formResponse(index);
                response.setCharacterEncoding(ServletUtil.UTF_8);
                response.setContentType(ServletUtil.APPLICATION_JSON);
                PrintWriter out = response.getWriter();
                out.print(messages);
                out.flush();
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "'token' parameter needed");
            }
        } catch (SAXException | ParserConfigurationException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("doPost");
        String data = ServletUtil.getMessageBody(request);
        logger.info(data);
        try {
            JSONObject json = MessageUtil.stringToJson(data);
            Message message = MessageUtil.jsonToMessage(json);
            MessageStorage.addMessage(message);
            messageDao.add(message);
        } catch (ParseException e) {
            logger.error(e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("doPut");
        String data = ServletUtil.getMessageBody(request);
        logger.info(data);
        try {
            JSONObject json = MessageUtil.stringToJson(data);
            Message message = MessageUtil.jsonToMessage(json);
            MessageStorage.addMessage(message);
            messageDao.update(message);
        } catch (ParseException e) {
            logger.error(e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("doDelete");
        String data = ServletUtil.getMessageBody(request);
        logger.info(data);
        try {
            JSONObject json = MessageUtil.stringToJson(data);
            Message message = MessageUtil.jsonToMessage(json);
            message.setText("");
            MessageStorage.addMessage(message);
            messageDao.delete(message.getId());
        } catch (ParseException e) {
            logger.error(e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @SuppressWarnings("unchecked")
    private String formResponse(int index) throws SAXException, IOException, ParserConfigurationException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(MessageUtil.MESSAGES, MessageStorage.getSubMessagesByIndex(index));
        jsonObject.put(MessageUtil.TOKEN, MessageUtil.getToken());
        return jsonObject.toJSONString();
    }

    private void loadHistory() throws SAXException, IOException, ParserConfigurationException, TransformerException {
        MessageStorage.addAll(messageDao.selectAll());
        logger.info(MessageStorage.getStorage());
    }

}