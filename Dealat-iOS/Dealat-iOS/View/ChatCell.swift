//
//  ChatCell.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 3/8/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class ChatCell: UICollectionViewCell {
  
    @IBOutlet weak var sellerLbl : UILabel!
    @IBOutlet weak var adLbl : UILabel!
    @IBOutlet weak var widthConstraint: NSLayoutConstraint!

    
    var chat : Chat!{
        didSet{
            self.sellerLbl.text = chat.seller_name
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
