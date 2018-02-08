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
    
    var imageName : String!{
        didSet{
            self.img.image = UIImage.init(named: imageName)
        }
    }
    
    
}
