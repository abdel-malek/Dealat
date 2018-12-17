//
//  HomeCell.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/6/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class HomeCell: BaseCell {

    @IBOutlet weak var img: UIImageView!
    
    
    @IBOutlet weak var rightLbl: UILabel!
    @IBOutlet weak var leftLbl: UILabel!
    
    @IBOutlet weak var leftImg: UIImageView!
    @IBOutlet weak var rightImg: UIImageView!

//    @IBOutlet weak var cntRight: UILabel!
//    @IBOutlet weak var cntLeft: UILabel!

    
    var cat : Cat!{
        didSet{
            Provider.sd_setImage(img, urlString: cat.mobile_image)
            
//            self.lbl.text = cat.category_name
//            self.lbl.textAlignment = (self.tag % 2 == 0) ? .right : .left
            
//            self.cntLeft.text = (self.tag % 2 == 0) ? "\(cat.ads_count2)" : nil
//            self.cntRight.text = (self.tag % 2 == 0) ? nil : "\(cat.ads_count2)"
            
            self.rightLbl.text = "\(cat.ads_count2)"
            self.rightImg.image = Provider.isArabic ? #imageLiteral(resourceName: "leftleft") : #imageLiteral(resourceName: "rightright")
            self.rightImg.isHidden = false
            
            self.leftLbl.text = cat.category_name
            self.leftImg.isHidden = true

            
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
