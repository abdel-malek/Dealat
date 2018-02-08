//
//  CategoryCell.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 2/6/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class CategoryCell: BaseCell {

    @IBOutlet weak var lbl: UILabel!
    
    var cat : Cat!{
        didSet{
            self.lbl.text = cat.category_name
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
