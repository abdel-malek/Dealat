//
//  CommericalCell.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/7/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class CommericalCell: UICollectionViewCell {
    
    @IBOutlet weak var img : UIImageView!
    @IBOutlet weak var lbl : UILabel!

    
    var imageName : String!{
        didSet{
            self.img.image = UIImage.init(named: imageName)
        }
    }
    
    
    var im : IMG! {
        didSet{
            Provider.sd_setImage(self.img, urlString: im.image)
        }
    }
    
    var newAdd : Bool!{
        didSet{
            self.lbl.text = "\(self.tag + 1)"
//            self.img.image = #imageLiteral(resourceName: "clothes")
        }
    }
    
    
    
}
