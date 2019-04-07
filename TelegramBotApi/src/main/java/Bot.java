import org.telegram.services.Emoji;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try{
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e){
            e.printStackTrace();
        }
    }

    static String a = "";

    public void sendMsg(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        // включение возможности разметки
        sendMessage.enableMarkdown(true);
        // установка id-чата, чтобы было понятно кому отвечать(в какой конкретно чат необходимо
        // отправить ответ)
        sendMessage.setChatId(message.getChatId().toString());
        // на какое конкретно сообщение нужно ответить
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        // отправка сообщения
        try{
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Model model = new Model();
        ModelCurrency modelCurrency = new ModelCurrency();
        ModelITNews modelITNews = new ModelITNews();
        Emoji emoji;
        emoji = Emoji.ELECTRIC_LIGHT_BULB;
        String str = "";
        str += emoji + "   Suggestions:   " + emoji + "\n\n";
        emoji = Emoji.SUN_BEHIND_CLOUD;
        str += emoji + "  To know weather : /weather" +"\n";
        emoji = Emoji.LAPTOP;
        str += emoji+ "  To know about top-headlines: /topHeadlines" + "\n";
        emoji = Emoji.BAR_CHART;
        str += emoji + "  To know about business news: /businessNews\n";
        emoji = Emoji.SCIENCE;
        str += emoji + "  To know about science news: /scienceNews\n";
        emoji = Emoji.BASKET_BALL;
        str += emoji + "  To know about sport news: /sportNews\n";
        emoji = Emoji.PENCIL;
        str += emoji+ "  To know about 3 the most top-headlines: /topHeadlines3" + "\n";
        emoji = Emoji.MONEY;
        str += emoji+ "  To know about currency rates: /currency" + "\n";
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()){
                case "/start":
                    sendMsg(message, str);
                    break;
                case "/help":
                    sendMsg(message, str);
                    break;
                case "/settings":
                    sendMsg(message, "What setting dou you want to do?");
                    break;
                case "/weather":
                    sendMsg(message, "Enter the name of the town, which weather you want to know.");
                    a = "weather";
                    break;
                case "/topHeadlines":
                    try{
                        sendMsg(message, TopHeadlinesNews.getTopHeadlinesNews(message.getText(), modelITNews));
                    } catch (IOException e) {
                        sendMsg(message, "Sorry, we don't have actual information :(");
                    }
                    break;
                case "/businessNews":
                    try{
                        sendMsg(message, BusinessNews.getBusinessNews(modelITNews));
                    } catch (IOException e) {
                        sendMsg(message, "Sorry, we don't have actual information :(");
                    }
                    break;
                case "/scienceNews":
                    try{
                        sendMsg(message, ScienceNews.getScienceNews(modelITNews));
                    } catch (IOException e) {
                        sendMsg(message, "Sorry, we don't have actual information :(");
                    }
                    break;
                case "/sportNews":
                    try{
                        sendMsg(message, SportNews.getSportNews(modelITNews));
                    } catch (IOException e) {
                        sendMsg(message, "Sorry, we don't have actual information :(");
                    }
                    break;
                case "/topHeadlines3":
                    try{
                        for (int i = 0; i < 4; i++)
                            sendMsg(message, TopHeadlines3.getTopHeadlinesNews(modelITNews, i));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case  "/currency":
                    sendMsg(message, "If you want to know exchange rates for all currencies: /currencyAll\n" +
                            "If you want to know exchange rates for main currencies(UAH, USD, EUR, PLN): /currencyMain");
                    break;
                case "/currencyAll":
                    try{
                        sendMsg(message, Currency.getCurrency(modelCurrency));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "/currencyMain":
                    try{
                        sendMsg(message, Currency.getCurrency());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    if (a == "weather") {
                        a = "";
                        try {
                            sendMsg(message, Weather.getWeather(message.getText(), model));
                        } catch (IOException e) {
                            sendMsg(message, " such town was not found.");
                        }
                    }
                    else
                    {
                        sendMsg(message, "I don't know such command. :((");
                    }
            }
        }
    }

    public void setButtons(SendMessage sendMessage){
        // инициализация клавиатуры
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        // устанавливаем разметку для нашей клавиатуры(связываем наше сообщение с нашей клавиатурой)
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        // параметры, которая будет выводить клавиатура определенным пользователям либо всем пользователям
        replyKeyboardMarkup.setSelective(true);

        // указываем подгонку клиенту под количество кнопок
        replyKeyboardMarkup.setResizeKeyboard(true);

        // параметр, указывающий скрывать нам клавиатуру после нажатия кнопки или не скрывать
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // создаем кнопки
        List<KeyboardRow> keyboardRowList = new ArrayList();
        // инициализируем первую строку клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("/help"));
        keyboardFirstRow.add(new KeyboardButton("/settings"));

        keyboardRowList.add(keyboardFirstRow);

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("/weather"));
        keyboardSecondRow.add(new KeyboardButton("/topHeadlines"));

        keyboardRowList.add(keyboardSecondRow);

        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add(new KeyboardButton("/businessNews"));
        keyboardThirdRow.add(new KeyboardButton("/currency"));

        keyboardRowList.add(keyboardThirdRow);
        // устанавливаем список на клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    public String getBotUsername() {
        return "iBot";
    }

    public String getBotToken()
    {
        return "735038538:AAGXF13CLGkWZ13nNO-oPr9nMC0iesFIj5Q";
    }
}
