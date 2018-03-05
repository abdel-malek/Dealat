//
//  MyFavoritesVC.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 3/5/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import XLPagerTabStrip

class MyFavoritesVC: BaseVC,UICollectionViewDelegate,UICollectionViewDataSource,UICollectionViewDelegateFlowLayout,IndicatorInfoProvider {
    
    @IBOutlet weak var collectionView : UICollectionView!
    var ads = [AD]()
    var itemInfo = IndicatorInfo(title: "Favorites".localized)
    
    override func viewDidLoad() {
        super.viewDidLoad()

        getData()
    }
    
    override func setupViews() {
        self.collectionView.delegate = self
        self.collectionView.dataSource = self
    }
    
    override func getRefreshing() {
        Communication.shared.get_my_favorites { (res) in
            self.hideLoading()
            self.ads = res
            self.collectionView.reloadData()
        }
    }

    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
            return ads.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! AdCell
            cell.ad = self.ads[indexPath.row]
            return cell
    }
    
    
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "AdDetailsBaseVC") as! AdDetailsBaseVC
        vc.tamplateId = self.ads[indexPath.row].tamplate_id.intValue
        vc.ad = self.ads[indexPath.row]
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        
        return CGSize.init(width: collectionView.frame.width, height: UIScreen.main.bounds.width / 2)
    }
    
    
    
    func indicatorInfo(for pagerTabStripController: PagerTabStripViewController) -> IndicatorInfo {
        return self.itemInfo
    }

}
