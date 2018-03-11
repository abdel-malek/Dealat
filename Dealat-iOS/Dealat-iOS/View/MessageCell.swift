//
//  MessageCell.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 3/11/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class MessageCell: BaseCell {
    
    @IBOutlet weak var vv1 : UIView!
    @IBOutlet weak var text1Lbl : ActiveLabel!
    
    @IBOutlet weak var vv2 : UIView!
    @IBOutlet weak var text2Lbl : ActiveLabel!
    
    
    var message : Message!{
        didSet{
            
            if let m = message{
                if !m.to_seller.Boolean{
                    self.vv1.isHidden = false
                    self.vv2.isHidden = true
                    self.text1Lbl.text = m.text
                }else{
                    self.vv2.isHidden = false
                    self.vv1.isHidden = true
                    self.text2Lbl.text = m.text
                }
            }
            
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()

        self.text1Lbl.enabledTypes = [.url]
        self.text2Lbl.enabledTypes = [.url]
        
        self.text1Lbl.handleURLTap {res  in
            if UIApplication.shared.canOpenURL(res){
                UIApplication.shared.openURL(res)
            }
        }
        self.text2Lbl.handleURLTap {res  in
            if UIApplication.shared.canOpenURL(res){
                UIApplication.shared.openURL(res)
            }
        }
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
    }
    
}
