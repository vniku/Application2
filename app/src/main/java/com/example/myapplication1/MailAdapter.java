package com.example.myapplication1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MailAdapter {
    //наши данные, которые мы будем инфлейтить
    List<Mail> mails=new ArrayList<>();
    //контекст
    Context context;
    //инфлейтор. Эта вещь нужна для отрисовки нашего макета на странице
    // отчасти его можно назвать инструментом для рендера
    LayoutInflater inflator;
    //конструктор. Требуется для передачи в адаптер контекста и данных
    public MailAdapter(List<Mail> mails, Context context) {
        this.mails=mails;
        this.context=context;
        //для инфлейтера мы берём
        inflator=LayoutInflater.from(context);
    }
    //1 из 3 обязательных методов
    // Возвращает количество, которое адаптер должен отобразить
    @Override
    public int getCount() {return mails.size();}
    //2 из 3 обязательных методов
    // возвращает объект, который должен отобразиться тем, который указан в аргументе
    @Override
    public Object getItem(int position) {return mails.get(position);}
    //3 из 3 обязательных методов
    // возвращает id, ассоциируемый с позицией в списке
    // при использовании алгоритмов лейзилоадинга это имеет смысл, но в этом случае не особо
    @Override
    public long getItemId(int position) {return position;}

    //календарь для работы с временем
    Calendar calendar=Calendar.getInstance();
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //получаем наше письмо и делаем его неизменяемым для работы с асинхронностями
    }
        Final Mail mail=mails.get(position);
        //если этот блок создаётся впервые то отрисовываем его
        if(convertView==null){
            convertView=inflator.inflate(R.layout.adapter_mail,parent,attachToRoot: false);
        }
        //биндинги
        TextView isNewMessageText=convertView.findViewById(R.id.isNewMessage);
        ImageView isNewMessageImage=convertView.findViewById(R.id.image);
        TextView title=convertView.fiindViewById(R.id.title);
        TextView date=convertView.findViewById(R.id.dateMessage);
        //если письмо прочитано-ставим соответствующую иконку и заголовок и цветом
        if(mail.isChecked()) {
            isNewMessageImage.setImageResource(R.drawable.ic_check_black_24dp);
            isNewMessageText.setText("прочитано");
            isNewMesssageText.setTextColor(Color.GRAY);
        }
        else
            {
            //иначе другую иконку
            isNewMessageImage.setImageResource(R.drawable.ic_fiber_new_black_24dp);
        }
        //отображаем наши данные
        title.setText(mail.getTitle());
        calendar.setTimeInMillis(mail.getDateInMS());
        date.setText(calendar.getTime().toString());
        convertView.setOnClickListener((v)->{
        Intent intent=new Intent(MailAdapter.class);
        Intent.putExtra("mail", mail);
        context.startActivity(intent);
    });
        //возвращаем доработанный нами View
    return convertView;
}
    //сеттер для данных
    void setMails(list<Mail> mails){this.mails=mails;}




}
