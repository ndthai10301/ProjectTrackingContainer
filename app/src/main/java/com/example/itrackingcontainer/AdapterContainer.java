package com.example.itrackingcontainer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterContainer extends BaseAdapter {
    List<Container> containerList;
    public AdapterContainer(List<Container> containerList)
    {
    this.containerList=containerList;
    }
    @Override
    public int getCount() {
        return containerList.size();
    }

    @Override
    public Object getItem(int position) {
        return containerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= LayoutInflater.from(viewGroup.getContext());
        view= inflater.inflate(R.layout.item_container_detail,viewGroup,false);
        TextView tvCont,tvVitri,tvNgayvaoBai,tvTrangthai;//tvNgayraBai,tvKichco,tvHangKT;
        tvCont=view.findViewById(R.id.tvResult_cont);
        tvVitri=view.findViewById(R.id.tvResult_vitri);
        tvTrangthai=view.findViewById(R.id.tvTrangthai);
        //tvKichco=view.findViewById(R.id.tvKichco);
        //tvHangKT=view.findViewById(R.id.tvHangKT);
        tvNgayvaoBai=view.findViewById(R.id.tvNgayvaoBai);
       // tvNgayraBai=view.findViewById(R.id.tvNgayraBai);
        Container container=containerList.get(i);
        //set gia tri
        String temp="",cBlock,cBay,cRow,cTier;
        temp=container.getcArea().toString();
        tvCont.setText(container.getCntrNo()+" / "+container.getOprID()+" / "+ container.getLocalSZPT()+" / "+ container.getStatus());
        switch (container.getCMStatus())
        {
            case "S": {tvTrangthai.setText(R.string.str_S);break;}
            case "D": {tvTrangthai.setText(R.string.str_D);break;}
            case "I": {tvTrangthai.setText(R.string.str_I);break;}
            case "O": {tvTrangthai.setText(R.string.str_O);break;}
            case "B": {tvTrangthai.setText(R.string.str_B);break;}
            default:{tvTrangthai.setText(R.string.str_NON);break;}
        }
        //tvHangKT.setText(container.getOprID());
//        if(!container.getLocalSZPT().isEmpty()) {
//            tvKichco.setText(container.getLocalSZPT());
//        }
//        else
//        {
//            tvKichco.setText("--");
//        }
        tvNgayvaoBai.setText(container.getDateIn());
       // tvNgayraBai.setText(container.getDateOut());
        if(!temp.equals("")||!temp.equals(null))
        {
            tvVitri.setText(container.getcBlock()+"-"+container.getcBay()+"-"+container.getcRow()+"-"+ container.getcTier());
        }
        else
        {
            tvVitri.setText(container.getcArea());
        }

        return view;
    }
}
