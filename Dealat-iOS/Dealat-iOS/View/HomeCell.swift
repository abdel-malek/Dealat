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
    @IBOutlet weak var lbl: UILabel!
    
    
    var cat : Cat!{
        didSet{
//           Provider.sd_setImage(img, urlString: cat.web_image)
            self.img.image = UIImage.init(named: cat.mobile_image)
            self.lbl.text = cat.category_name
            
            self.lbl.textAlignment = (self.tag % 2 == 0) ? .right : .left
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
