//
//  AdCell.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/7/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import AFDateHelper


class AdCell : UICollectionViewCell{
    @IBOutlet weak var img : UIImageView!
    @IBOutlet weak var nameLbl : UILabel!
    @IBOutlet weak var priceLbl : UILabel!
    @IBOutlet weak var viewsLbl : UILabel!
    @IBOutlet weak var dateLbl : UILabel!
    @IBOutlet weak var expiry_date : UILabel!

    
    @IBOutlet weak var imgStatus : UIImageView!

    
    var ad : AD!{
        didSet{
            //self.img.image = UIImage.init(named: ad.main_image)
            
            Provider.sd_setImage(self.img, urlString: ad.main_image)
            self.nameLbl.text = ad.title
            
            if ad.price.doubleValue == 0{
                self.priceLbl.text =  "Free".localized
            }else{
                self.priceLbl.text = ad.price.doubleValue.formatDigital() + " " + "S.P".localized
            }
            
            if ad.tamplate_id.intValue == 8{
                self.priceLbl.isHidden = true
            }else{
                self.priceLbl.isHidden = false
            }
            
            self.viewsLbl.text = " "//ad.show_period.stringValue
            if ad.publish_date != nil{
                let d = Date.init(fromString: ad.publish_date, format: .custom("yyyy-MM-dd hh:mm:ss"))
                self.dateLbl.text = d?.toString(format: DateFormatType.isoDate)
            }else{
                self.dateLbl.text = nil
            }
        }
    }
    
    var fromMyAds : Bool = false{
        didSet{
            self.imgStatus.image = self.ad.getStatus().1
            self.priceLbl.text = self.ad.getStatus().0
            
            self.expiry_date.text = nil
            
            if let date = self.ad.expiry_date, !date.isEmpty{
                if let d = Date.init(fromString: date, format: .custom("yyyy-MM-dd hh:mm:ss")){
                    self.expiry_date.text = "expires on \(d.toString(format: DateFormatType.isoDate))"
                }
            }
            
        }
    }
    
    
}


//class AdCell: BaseCell {
//
//    @IBOutlet weak var img : UIImageView!
//    @IBOutlet weak var nameLbl : UILabel!
//    @IBOutlet weak var priceLbl : UILabel!
//    @IBOutlet weak var viewsLbl : UILabel!
//    @IBOutlet weak var dateLbl : UILabel!
//
//
//    var ad : AD!{
//        didSet{
//            self.img.image = UIImage.init(named: ad.main_image)
////            self.nameLbl.text = ad.title
////            self.priceLbl.text = ad.price.stringValue
////            self.viewsLbl.text = ad.show_period.stringValue
////            self.dateLbl.text = ad.publish_date
//
//        }
//    }
//
//
//    override func awakeFromNib() {
//        super.awakeFromNib()
//        // Initialization code
//    }
//
//    override func setSelected(_ selected: Bool, animated: Bool) {
//        super.setSelected(selected, animated: animated)
//
//        // Configure the view for the selected state
//    }
//
//}

