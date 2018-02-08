//
//  AdCell.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/7/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class AdCell: BaseCell {
    
    @IBOutlet weak var img : UIImageView!
    @IBOutlet weak var nameLbl : UILabel!
    @IBOutlet weak var priceLbl : UILabel!
    @IBOutlet weak var viewsLbl : UILabel!
    @IBOutlet weak var dateLbl : UILabel!
    
    
    var ad : AD!{
        didSet{
            self.img.image = UIImage.init(named: ad.main_image)
//            self.nameLbl.text = ad.title
//            self.priceLbl.text = ad.price.stringValue
//            self.viewsLbl.text = ad.show_period.stringValue
//            self.dateLbl.text = ad.publish_date
            
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
