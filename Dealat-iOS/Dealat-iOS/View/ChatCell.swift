//
//  ChatCell.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 3/8/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class ChatCell: UICollectionViewCell {
  
    @IBOutlet weak var img: UIImageView!
    @IBOutlet weak var sellerLbl : UILabel!
    @IBOutlet weak var adLbl : UILabel!
    @IBOutlet weak var widthConstraint: NSLayoutConstraint!

    
    var chat : Chat!{
        didSet{
            let me = User.getCurrentUser()
            
            if let id = me.user_id, id == self.chat.seller_id.intValue{
                self.sellerLbl.text = chat.user_name
                if let i = self.chat.user_pic{
                    Provider.sd_setImage(self.img, urlString: i)
                }
                
            }else{
                self.sellerLbl.text = chat.seller_name
                if let i = self.chat.seller_pic{
                    Provider.sd_setImage(self.img, urlString: i)
                }
            }
            
            self.adLbl.text = chat.ad_title
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        self.contentView.translatesAutoresizingMaskIntoConstraints = false
        let screenWidth = UIScreen.main.bounds.size.width
        widthConstraint.constant = screenWidth - (2 * 12)
    }


}
