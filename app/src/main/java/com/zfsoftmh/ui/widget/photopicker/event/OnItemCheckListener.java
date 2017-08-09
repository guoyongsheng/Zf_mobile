package com.zfsoftmh.ui.widget.photopicker.event;


import com.zfsoftmh.ui.widget.photopicker.entity.Photo;

public interface OnItemCheckListener {

  /***
   *
   * @param position 所选图片的位置
   * @param path     所选的图片
   * @param selectedItemCount  已选数量
   * @return enable check
   */
  boolean onItemCheck(int position, Photo path, int selectedItemCount);

}
