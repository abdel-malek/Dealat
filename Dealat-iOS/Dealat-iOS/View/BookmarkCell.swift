//
//  BookmarkCell.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 4/11/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class BookmarkCell: UITableViewCell {

    
    @IBOutlet weak var keysLbl : UILabel!
    @IBOutlet weak var valuesLbl : UILabel!
    
    var parentVC : SavedSearchesVC?
    
    var bookmark : UserBookmark!{
        didSet{
            
            var keys = ""
            var values = ""
            
            if bookmark.query != nil{
                keys = bookmark.query.getStrings().0
                values = bookmark.query.getStrings().1
                
                print("00")
                print(bookmark.query.getStrings().0)
                
                print("11")
                print(bookmark.query.getStrings().1)
            }
            
            self.keysLbl.text = keys
            self.valuesLbl.text = values
        }
    }
    
    
    
    @IBAction func showBookmark(){
        parentVC?.showBookmark(self.bookmark)
    }
    
    @IBAction func deleteBookmark(){
        parentVC?.deleteBookmark(self.bookmark)
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
