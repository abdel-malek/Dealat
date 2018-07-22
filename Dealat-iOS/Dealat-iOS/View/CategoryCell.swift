//
//  CategoryCell.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/6/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class CategoryCell: BaseCell {

    @IBOutlet weak var lbl: UILabel!
    @IBOutlet weak var lblCount: UILabel!

    
    var cat : Cat!{
        didSet{
            self.lbl.text = cat.category_name
            self.lblCount.text = String(cat.ads_count2)
            
            if self.cat.children.count > 1 && self.tag != -1{
                self.accessoryType = .disclosureIndicator
            }else{
                self.accessoryType = .none
            }
            
            let border = CALayer()
            let width = CGFloat(0.5)
            border.borderColor = Theme.Color.red.cgColor
            border.frame = CGRect(x: 0, y: self.frame.size.height - width, width:  self.frame.size.width, height: width)
            
            border.borderWidth = width
            self.layer.addSublayer(border)
            self.layer.masksToBounds = true
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
