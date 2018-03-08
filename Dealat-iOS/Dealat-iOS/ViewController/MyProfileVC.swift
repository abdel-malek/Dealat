//
//  MyProfileVC.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 3/5/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import XLPagerTabStrip


class MyProfileVC: ButtonBarPagerTabStripViewController {

//    @IBOutlet weak var shadowView: UIView!
    let blueInstagramColor = UIColor(red: 37/255.0, green: 111/255.0, blue: 206/255.0, alpha: 1.0)
    
    override func viewDidLoad() {
        // change selected bar color
        settings.style.buttonBarBackgroundColor = .white
        settings.style.buttonBarItemBackgroundColor = .white
        settings.style.selectedBarBackgroundColor = blueInstagramColor
        settings.style.buttonBarItemFont = .boldSystemFont(ofSize: 14)
        settings.style.selectedBarHeight = 2.0
        settings.style.buttonBarMinimumLineSpacing = 0
        settings.style.buttonBarItemTitleColor = .black
        settings.style.buttonBarItemsShouldFillAvailableWidth = true
        settings.style.buttonBarLeftContentInset = 0
        settings.style.buttonBarRightContentInset = 0
        
        changeCurrentIndexProgressive = { [weak self] (oldCell: ButtonBarViewCell?, newCell: ButtonBarViewCell?, progressPercentage: CGFloat, changeCurrentIndex: Bool, animated: Bool) -> Void in
            guard changeCurrentIndex == true else { return }
            oldCell?.label.textColor = .black
            newCell?.label.textColor = self?.blueInstagramColor
        }
        super.viewDidLoad()
        
        self.title = "My Profile".localized
    }
    
    // MARK: - PagerTabStripDataSource
    
    override func viewControllers(for pagerTabStripController: PagerTabStripViewController) -> [UIViewController] {
        let child_1 = self.storyboard?.instantiateViewController(withIdentifier: "MyAdsVC") as! MyAdsVC
        let child_2 = self.storyboard?.instantiateViewController(withIdentifier: "MyFavoritesVC") as! MyFavoritesVC
        let child_3 = self.storyboard?.instantiateViewController(withIdentifier: "MyChatsVC") as! MyChatsVC
        
        return [child_1, child_2,child_3]
    }

}
