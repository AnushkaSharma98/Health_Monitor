package com.example.pksharma.healthmonitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter{

    Context context;
    ArrayList<Item> itemsList;
    LayoutInflater inflater;


    // Constructor
    public CustomAdapter(Context context, ArrayList<Item> itemsList){
        super(context,0);
        context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemsList = itemsList;
    }

    // This function return the number of different type of views that will be there in the list view.
    @Override
    public int getViewTypeCount(){
        return 2;
    }

    // This function returns the type of item(in our case header or list item) that adapter wants to know in getView function.
    @Override
    public int getItemViewType(int position) {
        return itemsList.get(position).getItemType();
    }

    // This function gives the total count of items that will be there in the list.
    @Override
    public int getCount() {
        return itemsList.size();
    }

    // This function returns the object of the itemList that has to inflated at that position.
    @Override
    public Object getItem(int position) {
        return itemsList.get(position);
    }


    // This function returns the unique id associated with every inflated layout, since it's is not useful in our case so
    // we return the position, which is also unique for every item.
    @Override
    public long getItemId(int position) {
        return position;
    }




    // This is the function in which we have to inflate the layout as per its TYPE
    // this function gets the type of each item from getItemViewType and on the basis of it we apply if else and inflate the layout.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View output = convertView;
        if(getItemViewType(position)==0){
            if(output==null){
                output = inflater.inflate(R.layout.bot_row_layout,parent,false);
                TextView botTextVIew = output.findViewById(R.id.botTextView);
                ReceivedMsgViewHolder receivedMsgViewHolder = new ReceivedMsgViewHolder(botTextVIew);
                output.setTag(receivedMsgViewHolder);
            }
            ReceivedMsgViewHolder receivedMsgViewHolder = (ReceivedMsgViewHolder) output.getTag();
            ReceivedMessage receivedMessage = (ReceivedMessage) itemsList.get(position);
            receivedMsgViewHolder.botTextView.setText(receivedMessage.message);
            return output;
        }else{
            if(output==null){
                output = inflater.inflate(R.layout.user_row_layout,parent,false);
                TextView userTextView = output.findViewById(R.id.userTextView);
                SentMsgViewHolder sentMsgViewHolder = new SentMsgViewHolder(userTextView);
                output.setTag(sentMsgViewHolder);
            }
            SentMsgViewHolder sentMsgViewHolder = (SentMsgViewHolder) output.getTag();
            SentMessage sentMessage = (SentMessage) itemsList.get(position);
            sentMsgViewHolder.userTextView.setText(sentMessage.message);
            return output;
        }
    }

}
